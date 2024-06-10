该SpringBoot应用程序能够接收GitHub Webhooks的POST请求，并在接收到请求后执行一个特定的shell脚本。

使用Docker方式部署。
```bash
git clone https://github.com/Baiye959/webhooks.git

cd webhooks

docker build -t webhooks-app .

docker run -d -p 9090:8080 webhooks-app
```