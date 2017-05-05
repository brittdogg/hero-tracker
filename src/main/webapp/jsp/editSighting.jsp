<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
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
                        <li role="presentation" class="active"><a href="${pageContext.request.contextPath}/sighting"><strong>Report Sighting</strong></a></li>
                        <li role="presentation"><a href="${pageContext.request.contextPath}/hero">Heroes</a></li>
                        <li role="presentation"><a href="${pageContext.request.contextPath}/organization">Organizations</a></li>
                        <li role="presentation"><a href="${pageContext.request.contextPath}/location">Places</a></li>
                    </ul>
                </div>
            </div>

            <!-- Add/Edit Sighting Form -->
            <div class="row">

                <div class="col-md-5">
                    <div>
                        <h3>Sighting Report<c:if test="${sighting != null}"> : ${sighting.location.address.city} - ${sighting.dateOccurred}</c:if></h3>
                        </div>
                        <form role="form" action="sighting/reportSighting" method="POST">
                            <div class="form-group">

                                <label for="sightingDate">
                                    Date:
                                </label>
                                <input class="form-control" name="sightingDate" type="date">
                            </div>
                            <div class="form-group">

                                <label for="sightingTime">
                                    Time (If Known):
                                </label>
                                <input class="form-control" name="sightingTime" type="time">
                            </div>
                            <div class="form-group">

                                <label for="sightingLocation">
                                    Location
                                </label>
                                <select class="form-control" name="locationId">
                                <c:forEach var="loc" items="${locations}">
                                    <option name="locationId" value="${loc.locationId}">${loc.name} - ${loc.address.city}, ${loc.address.state}</option>
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


                <!-- Add Heroes to the Sighting -->
                <div class="col-md-7">
                    <div>
                        <h3>Heroes Sighted</h3>
                        <br/>
                    </div>
                    <div class="col-md-12">
                        <c:if test="${empty heroSightings}">
                            <div class="row">
                                <span><strong>Add one or more heroes to this sighting.</strong></span>
                            </div>
                        </c:if>
                        <c:forEach var="heroSighting" items="${heroSightings}">
                            <div class="row">
                                <div class="col-md-2">
                                    <img class="heroThumb" height="50" width="50" alt="orgImg" src="https://s-media-cache-ak0.pinimg.com/736x/2a/23/55/2a2355413c128b319e04ae8f6ebe2a7f.jpg"/>
                                </div>
                                <div class="col-md-8">
                                    <a href="/HeroTracker/hero/editHero?heroId=${heroSighting.hero.heroId}"><strong>${heroSighting.hero.name}</strong></a>
                                </div>
                                <div class="col-md-2">
                                    <form role="form" method="POST" action="sighting/removeHeroSighting">
                                        <button type="submit"
                                                class="btn btn-danger"
                                                name="heroId"
                                                value="${heroSighting.heroSightingId}"><strong>X</strong></button>
                                    </form>
                                </div>
                            </div>
                        </c:forEach>
                        <div class="row">
                            <div class="col-md-2">
                                <img class="newThumb" height="50" width="50" alt="newImg" src="http://orig04.deviantart.net/fc6f/f/2011/202/c/9/superman_icon_2_by_jeremymallin-d417prm.png"/>
                            </div>
                            <div class="col-md-8">
                                <form role="form" id="addHeroSighting" method="POST" action="/HeroTracker/sighting/addHeroSighting">
                                    <select name="heroId" class="form-control">
                                        <option selected>Add New...</option>
                                        <c:forEach var="hero" items="${heroes}">
                                            <option value="${hero.heroId}">${hero.name}</option>
                                        </c:forEach>
                                    </select>
                                    <input type="hidden" name="sightingId" value="${heroSighting.sighting.sightingId}"/>
                                    <input type="hidden" name="origin" value="sighting"/>
                                </form>
                            </div>
                            <div class="col-md-2">
                                <button type="submit"
                                        form="addHeroSighting"
                                        class="btn btn-success"><strong>+</strong></button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    </body>
</html>