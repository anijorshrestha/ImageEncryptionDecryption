<%--
  Created by IntelliJ IDEA.
  User: anijor
  Date: 10/25/2016
  Time: 4:01 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Image Encryption and Decryption</title>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'login.css')}" type="text/css">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'bootstrap.min.css')}" type="text/css">
</head>

<body>
<div class="jumbotron head">
    <h1 class="text-center">Image Encryption and Decryption</h1>
</div>

<div class="container-fluid">

    <div class="login-page">

            <g:form class="form" controller="image" enctype="multipart/form-data">

                <legend><h3>Image to Encrypt or Decrypt</h3></legend>

                <input class="form-group" type="file" name="photo" id="photo"/>

                <div class="form-group">
                    <label><h3>Input Key</h3></label>
                    <input class="form-control" type="text" name="user_key"/>

                </div>


                <g:actionSubmit class="btn btn-danger" value="Encrypt" action="save"/>


                <g:actionSubmit class="btn btn-danger" value="Decrypt" action="saveD"/>

            </g:form>
        </div>



</div>



