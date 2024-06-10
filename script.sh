#!/bin/bash
cd /root/vuepress-reco2
git pull
cp -rf .vuepress/dist/ ~
rm -r /root/html
mv /root/dist /root/html
cp -rf /root/html /var/www