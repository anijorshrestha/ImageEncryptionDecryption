<%--
  Created by IntelliJ IDEA.
  User: anijor
  Date: 10/25/2016
  Time: 4:01 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
</head>

<body>
<g:form controller="encryption" action="save" enctype="multipart/form-data">
    <div>
        <label>Photo</label>
        <input type="file"  name="photo"  id="photo"/><br>
    </div>
    <g:submitButton name="submit" class="btn btn-primary pull-right" value="Save"/>
</g:form>

</body>
</html>