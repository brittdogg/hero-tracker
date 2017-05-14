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
                        <li role="presentation"><a href="${pageContext.request.contextPath}/hero">Heroes</a></li>
                        <li role="presentation"><a href="${pageContext.request.contextPath}/organization">Organizations</a></li>
                        <li role="presentation" class="active"><a href="${pageContext.request.contextPath}/location">Places</a></li>
                    </ul>
                </div>
            </div>

            <!-- Add New Location Form -->
            <div class="row">
                <div class="col-md-5">
                    <div>
                        <h3>Report New Hotspot</h3>
                    </div>
                    <form role="form" action="location/createLocation" method="POST">
                        <div class="form-group">

                            <label for="locName">
                                Location Name
                            </label>
                            <input class="form-control" name="locName" type="text">
                        </div>
                        <div class="form-group">

                            <label for="locDescription">
                                Description
                            </label>
                            <input class="form-control" name="locDescription" type="text">
                        </div>
                        <div class="form-group">

                            <label for="locStreet1">
                                Street 1
                            </label>
                            <input class="form-control" name="locStreet1" type="text">
                        </div>
                        <div class="form-group">

                            <label for="locStreet2">
                                Street 2
                            </label>
                            <input class="form-control" name="locStreet2" type="text">
                        </div>
                        <div class="form-group">

                            <label for="locCity">
                                City
                            </label>
                            <input class="form-control" name="locCity" type="text">
                        </div>
                        <div class="form-group">

                            <label for="locState">
                                State
                            </label>
                            <input class="form-control" name="locState" type="text">
                        </div>
                        <div class="form-group">

                            <label for="locZip">
                                Zip
                            </label>
                            <input class="form-control" name="locZip" type="text">
                        </div>
                        <div class="form-group">
                            <div class="col-md-8">

                                <label for="latDegrees">Latitude</label>
                                <input class="form-control" name="latDegrees" type="text">
                            </div>
                            <div class="col-md-4">
                                
                            <label for="latDirection">*</label>
                            <select class="form-control">
                                <option name="latDirection" value="N" selected>N</option>
                                <option name="latDirection" value="S">S</option>
                            </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-8">

                                <label for="longDegrees">Longitude</label>
                                <input class="form-control" name="longDegrees" type="text">
                            </div>
                            <div class="col-md-4">
                                
                            <label for="longDirection">*</label>
                            <select class="form-control">
                                <option name="longDirection" value="W" selected>W</option>
                                <option name="longDirection" value="E">E</option>
                            </select>
                            </div>
                        </div>
                        <div class="row" style="text-align: right; padding-right: 15px">
                            <button type="submit" class="btn btn-primary">
                                <strong>Submit</strong>
                            </button>
                        </div>
                    </form>
                </div>

                <!-- Location Information Ticker -->

                <div class="col-md-7">
                    <div>
                        <h3>Browse Hotspots</h3>
                        <br/>
                    </div>
                    <div class="col-md-12">
                        <c:forEach var="loc" items="${locations}">
                            <div class="row">
                                <div class="col-md-2">
                                    <img class="orgThumb" height="50" width="50" alt="locImg" src="https://s-media-cache-ak0.pinimg.com/736x/2a/23/55/2a2355413c128b319e04ae8f6ebe2a7f.jpg"/>
                                </div>
                                <div class="col-md-8">
                                    <a href="${pageContext.request.contextPath}/location/editLocation?orgId=${loc.locationId}"><strong>${loc.name} - ${loc.address.city}, ${loc.address.state}</strong></a>
                                </div>
                                <div class="col-md-2">
                                    <form role="form" method="POST" action="location/deleteLocation">
                                        <button type="submit"
                                                class="btn btn-danger"
                                                name="locationId"
                                                value="${loc.locationId}"><strong>X</strong></button>
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