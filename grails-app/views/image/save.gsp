<html>
<head>
    <title>Image Encryption and Decryption</title>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'login.css')}" type="text/css">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'bootstrap.min.css')}" type="text/css">
</head>

<body>
<div class="jumbotron head" >
    <h1 class="text-center">Image Encryption and Decryption</h1>
        <g:link class="btn btn-default pull-right" style="margin-right: 10%;"  controller="encryption" action="renderFinalView">Home</g:link>
</div>

<div class="container">

    <div class="login-page"    style="text-align: -webkit-center;" >
        <div class="col-lg-12">
            <h2>Comparion between AES and RSA</h2>
        </div>

        <div class="login-page col-lg-6" style="text-align: -webkit-center;" >
            <h2>AES</h2>
            <label >Original</label><br>
            <img src="../${original_photo}"><br>
            <label >Encrypted</label><br>
            <img src="${resource(dir:"images", file: "AES-Algorithm-Encrypted.jpg") }"><br>
            <label >Decrypted</label><br>
            <img src="${resource(dir:"images", file: "AES-Algorithm-Decrypted.jpg") }"><br>
            <br>
            <label >NPCR</label><br>
            <input type="text" value="${aes_map.getAt("aes_npr")}" readonly><br>
            <label >Correlation Coefficient</label><br>
            <input type="text" value="${aes_map.getAt("aes_correlation")}" readonly>
            <label >Encryption Time</label><br>
            <input type="text" value="${aes_map.getAt("aes_encryption_time")}" readonly>

        </div>
        <div class="login-page col-lg-6" style="text-align: -webkit-center;" >
            <h2>RSA</h2>
            <label >Original</label><br>
            <img src="../photo.jpg"><br>
            <label >Encrypted</label><br>
            <img src="${resource(dir:"images", file: "RSA-Algorithm-Encrypted") }"><br>
            <label >Decrypted</label><br>
            <img src="${resource(dir:"images", file: "RSA-Algorithm-Decrypted") }"><br>
            <br>
            <label >NPCR</label><br>
            <input type="text" value="${rsa_map.getAt("rsa_npr")}" readonly><br>
            <label >Correlation Coefficient</label><br>
            <input type="text" value="${rsa_map.getAt("rsa_correlation")}" readonly>
            <label >Encryption Time</label><br>
            <input type="text" value="${rsa_map.getAt("rsa_encryption_time")}" readonly>
            <br>
            <br>

        </div>


    </div>
    </div>
</body>
</html>

