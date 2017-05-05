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
                <div class="col-md-12">
                    <div>
                        <h3>Sighting Report<c:if test="${sighting != null}"> : ${sighting.location.address.city} - ${sighting.dateOccurred}</c:if></h3>
                        </div>
                        <form role="form" action="/HeroTracker/sighting/reportSighting" method="POST">
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
                        <div class="row" style="text-align: center; padding-right: 15px">
                            <button type="submit" class="btn btn-primary">
                                <strong>Submit and Add Heroes</strong>
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>


        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    </body>
</html>