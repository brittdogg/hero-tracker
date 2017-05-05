<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <title>Hero Tracker</title>

        <meta name="description" content="Source code generated using layoutit.com">
        <meta name="author" content="LayoutIt!">

        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">

    </head>
    <body>
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <div class="page-header">
                        <h1>
                            Hero Tracker <small>Totally Not For Killing Them</small>
                        </h1>
                    </div> 
                    <ul class="nav nav-tabs">
                        <li role="presentation"><a href="${pageContext.request.contextPath}/home">Home</a></li>
                        <li role="presentation"><a href="${pageContext.request.contextPath}/sighting"><strong>Report Sighting</strong></a></li>
                        <li role="presentation" class="active"><a href="${pageContext.request.contextPath}/hero">Heroes</a></li>
                        <li role="presentation"><a href="${pageContext.request.contextPath}/organization">Organizations</a></li>
                        <li role="presentation"><a href="${pageContext.request.contextPath}/location">Places</a></li>
                    </ul>
                </div>
            </div>
            <div class="row">
                <div class="col-md-5">
                    <div>
                        <h3>Report New Hero</h3>
                    </div>
                    <form role="form" action="hero/createHero" method="POST">
                        <div class="form-group">

                            <label for="heroName">
                                Name
                            </label>
                            <input class="form-control" name="heroName" type="text">
                        </div>
                        <div class="form-group">

                            <label for="heroDescription">
                                Description
                            </label>
                            <input class="form-control" name="heroDescription" type="text">
                        </div>
                        <div class="form-group">
                            <label for="superPower">
                                Superpower
                            </label>
                            <select name="superPower" class="form-control">
                                <c:forEach var="power" items="${powers}">
                                    <option name="superPower" value="${power.superPowerId}">${power.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="row" style="text-align: right; padding-right: 15px">
                        <button type="submit" class="btn btn-primary">
                            <strong>Submit</strong>
                        </button>
                        </div>
                    </form>
                </div>
                <div class="col-md-7">
                    <div>
                        <h3>Browse Heroes</h3>
                        <br/>
                    </div>
                    <div class="col-md-12">
                        <c:forEach var="hero" items="${heroes}">
                            <div class="row">
                                <div class="col-md-2">
                                    <img class="heroThumb" height="50" width="50" alt="heroImg" src="https://s-media-cache-ak0.pinimg.com/736x/2a/23/55/2a2355413c128b319e04ae8f6ebe2a7f.jpg"/>
                                </div>
                                <div class="col-md-8">
                                    <a href="/HeroTracker/hero/editHero?heroId=${hero.heroId}"><strong>${hero.name}</strong> - <em>${hero.power.name}</em></a>
                                </div>
                                <div class="col-md-2">
                                    <form role="form" method="POST" action="hero/deleteHero">
                                        <button type="submit"
                                                class="btn btn-danger"
                                                name="heroId"
                                                value="${hero.heroId}"><strong>X</strong></button>
                                    </form>
                                </div>
                        </div>
                            </c:forEach>
                    </div>
                </div>
            </div>
        </div>

        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    </body>
</html>