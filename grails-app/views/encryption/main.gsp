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

        <g:form controller="image" action="save" enctype="multipart/form-data">
            <div>
                <table>
                    <tr>
                        <td>
                            <label>Choose Image to Encrypt</label>
                        </td>

                    </tr>
                    <tr>
                        <td>
                            <input type="file"  name="photo"  id="photo"/>
                        </td>
                        <td>
                            <button>Save</button>

                        </td>
                    </tr>
                </table>



            </div>

        </g:form>
    </div>

</div>



