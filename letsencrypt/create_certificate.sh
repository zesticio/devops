#!/bin/bash
sudo certbot certonly --manual --preferred-challenges=dns \
	--email deebendu.kumar@zestic.in \
	--server https://acme-v02.api.letsencrypt.org/directory \
	--work-dir=. --config-dir=. --logs-dir=. \
	--agree-tos \
	-d api.dev.zestic.in
