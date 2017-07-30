<html>
<head>
    <title>Image Encryption and Decryption</title>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'login.css')}" type="text/css">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'bootstrap.min.css')}" type="text/css">
</head>

<body>
<div class="jumbotron head">
    <h1 class="text-center">Image Encryption and Decryption</h1>
    <g:link class="btn btn-default pull-right" style="margin-right: 10%;"  controller="encryption" action="renderFinalView">Home</g:link>

</div>

<div class="container-fluid">

    <div class="login-page col-lg-6" style="text-align: -webkit-center;" >


        <img src="${resource(dir:"images", file: "ag.jpg") }">
        <label >NPCR</label>
        <input type="text" value="123" readonly disabled>
        <label >Correlation Coefficient</label>
        <input type="text" value="123" readonly disabled>


    </div>
    <div class="login-page col-lg-6" style="text-align: -webkit-center;" >
        <img src="${resource(dir:"images", file: "ag.jpg") }">
        <label >NPCR</label>
        <input type="text" value="123" readonly>
        <label >Correlation Coefficient</label>
        <input type="text" value="123" readonly>


    </div>
</div>
</body>
</html>

