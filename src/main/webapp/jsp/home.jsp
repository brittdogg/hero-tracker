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
                <div class="page-header">
                    <h1>
                        Hero Tracker <small>Totally Not For Killing Them</small>
                    </h1>
                </div>
                <div class="col-md-12">
                    <ul class="nav nav-tabs">
                        <li role="presentation" class="active"><a href="${pageContext.request.contextPath}/home">Home</a></li>
                        <li role="presentation"><a href="${pageContext.request.contextPath}/sighting"><strong>Report Sighting</strong></a></li>
                        <li role="presentation"><a href="${pageContext.request.contextPath}/hero">Heroes</a></li>
                        <li role="presentation"><a href="${pageContext.request.contextPath}/organization">Organizations</a></li>
                        <li role="presentation"><a href="${pageContext.request.contextPath}/location">Places</a></li>
                    </ul>
                </div>
            </div>

            <div class="row">
                <div class="col-md-5">
                    <div>
                        <h3>Report New Hero Sighting</h3>
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
                                <strong>Continue</strong>
                            </button>
                        </div>
                    </form>
                </div>

                <div class="col-md-7">
                    <div>
                        <h3>Recent Sightings</h3>
                    </div>
                    <div class="col-md-12">
                        <c:forEach var="heroSighting" items="${sightings}">
                            <!-- parse date -->
                            <fmt:parseDate value="${heroSighting.sighting.dateOccurred}" pattern="yyyy-MM-dd" 
                                           var="parsedDate" type="date" />
                            <fmt:formatDate value="${parsedDate}" var="sightingDate" 
                                            type="date" pattern="MM.dd.yyyy" />

                            <div class="row">

                                <div class="col-md-3">
                                    <strong><c:out value="${sightingDate}"/></strong><br/>
                                    <em><c:out value="${heroSighting.sighting.location.address.city}, ${heroSighting.sighting.location.address.state}"/></em>
                                </div>
                                <div class="col-md-2">
                                    <img class="sightingThumb" height="50" width="50" src="https://s-media-cache-ak0.pinimg.com/736x/2a/23/55/2a2355413c128b319e04ae8f6ebe2a7f.jpg" />
                                </div>
                                <div class="col-md-7">
                                    <a href="#">
                                        <strong><c:out value="${heroSighting.hero.name}"/></strong>
                                    </a>
                                </div>
                            </div>

                            <!-- build list item row -->
                            <!--<a class="list-group-item active"><span class="badge">14</span>More...</a>-->
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>

        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    </body>
</html>