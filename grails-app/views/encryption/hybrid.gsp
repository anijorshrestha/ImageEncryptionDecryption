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
    <script src="${resource(dir: 'js',file: 'bootstrap.min.js')}" type="text/javascript"></script>
    <script src="${resource(dir: 'js',file: 'jquery.js')}" type="text/javascript"></script>
    <script>
        function encryptImage(){
            var keytobeEncrypted = $('#encryptionkey').val();

            var data = {
                key : keytobeEncrypted
            }
            $.ajax({
                url : '${createLink(controller: 'decrypt', action: 'RSAkeyencryption')}',
                type : 'POST',
                data : data,
                success: function(resp){
                    $('#keyafterenc').val(resp);
                }
            })
        }
        function decryptImage(){
            var decrypt = $('#decryptvalue').val();
            var data = {
                user_key: decrypt
            }
            $.ajax({
                url : '${createLink(controller: 'decrypt', action: 'RSAkeydecryption')}',
                type : 'POST',
                data : data,
                success: function(resp){
                    $('#keyafterdec').val(resp);
                }
            })
        }
    </script>
</head>

<body>
<div class="jumbotron head">
    <h1 class="text-center">Image Encryption and Decryption</h1>
</div>
<div class="container-fluid">
    <div class="col-md-6">
    <div class="panel panel-danger">
        <div class="panel-heading">AES for image encryption</div>
        <div class="panel-body">
            <g:form controller="decrypt" enctype="multipart/form-data">
                <div>
                    <table>
                        <tr>
                            <td>
                                <label>Choose Image to Encrypt</label>
                            </td>

                        </tr>
                        <tr>
                            <td>
                                <input type="file"  name="photo"  id="photo1"/>
                            </td>


                        </tr>
                        <tr>
                            <td></td>
                        </tr>
                        <tr>
                            <td>
                                <label>Input Key</label>
                            </td>

                        </tr>
                        <tr>
                            <td>
                                <input type="text"  name="user_key" id = "user_key"/>
                            </td>


                        </tr>
                        <tr>
                            <td><g:actionSubmit class="btn btn-danger" value="Encrypt" action="save"/></td>


                            <td> <g:actionSubmit class="btn btn-danger" value="Decrypt" action="saveD"/></td>

                        </tr>
                    </table>



                </div>

            </g:form>
        </div>
        </div>
    </div>

    <div class="col-md-6">
            <div class="panel panel-danger">
                <div class="panel-heading">RSA for key encryption</div>
                <div class="panel-body">
                    <table class="table">
                        <thead>
                        <th>
                            Encryption
                        </th>
                        <th>
                            Decryption
                        </th>
                        </thead>
                        <tbody>
                        <tr>
                            <td>

                                    <div>
                                        <table>
                                            <tr>
                                                <td>
                                                    <label>Enter key </label>
                                                </td>

                                            </tr>
                                            <tr>
                                                <td>
                                                    <input type="text"  name="text"  id="encryptionkey"/>
                                                </td>


                                            </tr>
                                            <tr>
                                                <td>
                                                    <label>Enter Public Key</label>
                                                </td>

                                            </tr>
                                            <tr>
                                                <td>
                                                    <input type="text"  name="user_key" id ="keyafterenc"/>
                                                </td>


                                            </tr>
                                            <tr>
                                                <td>
                                                    <button onclick="encryptImage()">Encrypt</button>
                                                </td>

                                            </tr>
                                        </table>



                                    </div>



                            </td>
                            <td>

                                    <div>
                                        <table>
                                            <tr>
                                                <td>
                                                    <label>Enter key </label>
                                                </td>

                                            </tr>
                                            <tr>
                                                <td>
                                                    <input type="text"  name="text"  id="decryptvalue"/>
                                                </td>


                                            </tr>
                                            <tr>
                                                <td>
                                                    <label>Enter Private Key</label>
                                                </td>

                                            </tr>
                                            <tr>
                                                <td>
                                                    <input type="text"  name="user_key" id = "keyafterdec"/>
                                                </td>


                                            </tr>
                                            <tr>

                                                <td>
                                                    <button onclick="decryptImage()">Decrypt</button>

                                                </td>

                                            </tr>
                                        </table>



                                    </div>


                            </td>
                        </tr>
                        </tbody>
                    </table>



                </div>
            </div>

        </div>



</div>



