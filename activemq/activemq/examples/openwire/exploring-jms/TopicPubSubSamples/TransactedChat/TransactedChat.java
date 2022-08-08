/*
 * � 2001-2009, Progress Software Corporation and/or its subsidiaries or affiliates.  All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 
Sample Application

Writing a Basic JMS Application using
    - Publish and Subscribe
    - Transacted Sessions
    - Multiple Sessions

Run this program to publish and subscribe to the specified topic.
Messages are buffered and sent when a specific string is seen ("COMMIT").
Messages buffered can be discarded by entering a specified string ("CANCEL").

Usage:
  java TransactedChat -b <broker:port> -u <username> -p <password>
      -b broker:port points to your message broker
                     Default: tcp://localhost:61616
      -u username    must be unique (but is not checked)
      -p password    password for user (not checked)

Suggested demonstration:
  - In a console window with the environment set, start this
    application. In other console windows start other sessions
    under different user names. For example:
       java TransactedChat -u ADMINISTRATION
       java TransactedChat -u FACILITIES
  - Type some text and then press Enter.
  - Repeat to create a batch of messages.
  - Send the batched messages by entering the text "COMMIT"
  - Discard the batched messages by entering the text "CANCEL"
    

*/
import org.apache.activemq.*;

public class TransactedChat
    implements javax.jms.MessageListener
{
    private static final String APP_TOPIC = "jms.samples.chat";
    private static final String DEFAULT_BROKER_NAME = "tcp://localhost:61616";
    private static final String DEFAULT_PASSWORD = "password";

    private javax.jms.Connection connect = null;
    private javax.jms.Session publishSession = null;
    private javax.jms.Session subscribeSession = null;
    private javax.jms.MessageProducer publisher = null;

    /** Create JMS client for publishing and subscribing to messages. */
    private void chatter( String broker, String username, String password)
    {
        // Create a connection.
        try
        {
            javax.jms.ConnectionFactory factory;
            factory = new ActiveMQConnectionFactory(username, password, broker);
            connect = factory.createConnection (username, password);
            // We want to be able up commit/rollback messages published,
            // but not affect messages consumed. Therefore, we need two sessions.
            publishSession = connect.createSession(true, javax.jms.Session.AUTO_ACKNOWLEDGE);
            subscribeSession = connect.createSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);
        }
        catch (javax.jms.JMSException jmse)
        {
            System.err.println("error: Cannot connect to Broker - " + broker);
            jmse.printStackTrace();
            System.exit(1);
        }

        // Create Publisher and Subscriber to 'chat' topics
        try
        {
            javax.jms.Topic topic = subscribeSession.createTopic (APP_TOPIC);
            javax.jms.MessageConsumer subscriber = subscribeSession.createConsumer(topic);
            subscriber.setMessageListener(this);
            publisher = publishSession.createProducer(topic);
            // Now start the Connection
            connect.start();
        }
        catch (javax.jms.JMSException jmse)
        {
            jmse.printStackTrace();
        }

        try
        {
            // Read all standard input and send it as a message.
            java.io.BufferedReader stdin =
                new java.io.BufferedReader( new java.io.InputStreamReader( System.in ) );
            boolean showMessage = true;
            while ( true )
            {
                if (showMessage)
                {
                    System.out.println ("TransactedChat application:");
	                System.out.println ("===========================" );
                    System.out.println ("The application user " + username + " connects to the broker at " + DEFAULT_BROKER_NAME + ".");
					System.out.println ("The application will stage messages to the " + APP_TOPIC + " topic until you either commit them or roll them back.");
				    System.out.println ("The application also subscribes to that topic to consume any committed messages published there.\n");
                    System.out.println ("1. Enter text to publish and then press Enter to stage the message.");
                    System.out.println ("2. Add a few messages to the transaction batch.");
                    System.out.println ("3. Then, either:");
                    System.out.println ("     o Enter the text 'COMMIT', and press Enter to publish all the staged messages.");
                    System.out.println ("     o Enter the text 'CANCEL', and press Enter to drop the staged messages waiting to be sent.");
                    showMessage = false;
                }
                String s = stdin.readLine();

                if ( s == null )
                    exit();
                else if (s.trim().equals("CANCEL"))
                {
                    // Rollback the messages. A new transaction is implicitly
                    // started for following messages.
                    System.out.println ("Cancelling messages...");
                    publishSession.rollback();
                    System.out.println ("Staged messages have been cleared.");
                    showMessage = false; // don't show the help message again.
                }
                else if ( s.length() > 0 )
                // See if we should send the messages
                  if (s.trim().equals("COMMIT"))
                  {
                        // Commit (send) the messages. A new transaction is
                        // implicitly  started for following messages.
                        System.out.println ("Committing messages... ");
                        publishSession.commit();
                        System.out.println ("Staged messages have all been sent.");
                        showMessage = false; // dont't show the help message again.
                  }
                  else
                   {
                    javax.jms.TextMessage msg = publishSession.createTextMessage();
                    msg.setText( username + ": " + s );
                    // Publish the message persistently
                    publisher.send( msg );
                   }
            }
        }
        catch ( java.io.IOException ioe )
        {
            ioe.printStackTrace();
        }
        catch ( javax.jms.JMSException jmse )
        {
            jmse.printStackTrace();
        }
    }

    /**
     * Handle the message
     * (as specified in the javax.jms.MessageListener interface).
     */
    public void onMessage( javax.jms.Message aMessage)
    {
        try
        {
            // Cast the message as a text message.
            javax.jms.TextMessage textMessage = (javax.jms.TextMessage) aMessage;

            // This handler reads a single String from the
            // message and prints it to the standard output.
            try
            {
                String string = textMessage.getText();
                System.out.println( string );
            }
            catch (javax.jms.JMSException jmse)
            {
                jmse.printStackTrace();
            }
        }
        catch (java.lang.RuntimeException rte)
        {
            rte.printStackTrace();
        }
    }

    /** Cleanup resources cleanly and exit. */
    private void exit()
    {
        try
        {
            publishSession.rollback(); // Rollback any uncommitted messages.
            connect.close();
        }
        catch (javax.jms.JMSException jmse)
        {
            jmse.printStackTrace();
        }

        System.exit(0);
    }

    //
    // NOTE: the remainder of this sample deals with reading arguments
    // and does not utilize any JMS classes or code.
    //

    /** Main program entry point. */
    public static void main(String argv[]) {

        // Is there anything to do?
        if (argv.length == 0) {
            printUsage();
            System.exit(1);
        }

        // Values to be read from parameters
        String broker    = DEFAULT_BROKER_NAME;
        String username  = null;
        String password  = DEFAULT_PASSWORD;

        // Check parameters
        for (int i = 0; i < argv.length; i++) {
            String arg = argv[i];


            if (arg.equals("-b")) {
                if (i == argv.length - 1 || argv[i+1].startsWith("-")) {
                    System.err.println("error: missing broker name:port");
                    System.exit(1);
                }
                broker = argv[++i];
                continue;
            }

            if (arg.equals("-u")) {
                if (i == argv.length - 1 || argv[i+1].startsWith("-")) {
                    System.err.println("error: missing user name");
                    System.exit(1);
                }
                username = argv[++i];
                continue;
            }

            if (arg.equals("-p")) {
                if (i == argv.length - 1 || argv[i+1].startsWith("-")) {
                    System.err.println("error: missing password");
                    System.exit(1);
                }
                password = argv[++i];
                continue;
            }

            if (arg.equals("-h")) {
                printUsage();
                System.exit(1);
            }

            // Invalid argument
            System.err.println ("error: unexpected argument: "+arg);
            printUsage();
            System.exit(1);
        }

        // Check values read in.
        if (username == null) {
            System.err.println ("error: user name must be supplied");
            printUsage();
        }

        // Start the JMS client for the "chat".
        TransactedChat chat = new TransactedChat();
        chat.chatter (broker, username, password);

    }

    /** Prints the usage. */
    private static void printUsage() {

        StringBuffer use = new StringBuffer();
        use.append("usage: java TransactedChat (options) ...\n\n");
        use.append("options:\n");
        use.append("  -b name:port Specify name:port of broker.\n");
        use.append("               Default broker: "+DEFAULT_BROKER_NAME+"\n");
        use.append("  -u name      Specify unique user name. (Required)\n");
        use.append("  -p password  Specify password for user.\n");
        use.append("               Default password: "+DEFAULT_PASSWORD+"\n");
        use.append("  -h           This help screen.\n");
        System.err.println (use);
    }
}
