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
                        <li role="presentation" class="active"><a href="${pageContext.request.contextPath}/organization">Organizations</a></li>
                        <li role="presentation"><a href="${pageContext.request.contextPath}/location">Places</a></li>
                    </ul>
                </div>
            </div>

            <!-- Add New Organization Form -->
            <div class="row">
                <div class="col-md-5">
                    <div>
                        <h3>Report New Organization</h3>
                    </div>
                    <form role="form" action="organization/createOrg" method="POST">
                        <div class="form-group">

                            <label for="orgName">
                                Name
                            </label>
                            <input class="form-control" name="orgName" type="text">
                        </div>
                        <div class="form-group">

                            <label for="orgDescription">
                                Description
                            </label>
                            <input class="form-control" name="orgDescription" type="text">
                        </div>
                        <div class="form-group">

                            <label for="orgLocation">
                                Headquarters
                            </label>
                            <select class="form-control" name="addressId">
                                <c:forEach var="loc" items="${locations}">
                                    <option name="addressId" value="${loc.address.addressId}">${loc.name} - ${loc.address.city}, ${loc.address.state}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">

                            <label for="orgContactName">
                                Contact Name
                            </label>
                            <input class="form-control" name="orgContact" type="text">
                        </div>
                        <div class="form-group">

                            <label for="orgContactPhone">
                                Contact Phone
                            </label>
                            <input class="form-control" name="orgPhone" type="text">
                        </div>
                        <div class="form-group">

                            <label for="orgContactEmail">
                                Contact Email
                            </label>
                            <input class="form-control" name="orgEmail" type="text">
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
                        <h3>Browse Hero Organizations</h3>
                        <br/>
                    </div>
                    <div class="col-md-12">
                        <c:forEach var="org" items="${orgs}">
                            <div class="row">
                                <div class="col-md-2">
                                    <img class="orgThumb" height="50" width="50" alt="orgImg" src="https://s-media-cache-ak0.pinimg.com/736x/2a/23/55/2a2355413c128b319e04ae8f6ebe2a7f.jpg"/>
                                </div>
                                <div class="col-md-8">
                                    <a href="/HeroTracker/organization/editOrg?orgId=${org.orgId}"><strong>${org.name}</strong></a>
                                </div>
                                <div class="col-md-2">
                                    <form role="form" method="POST" action="organization/deleteOrg">
                                        <button type="submit"
                                                class="btn btn-danger"
                                                name="orgId"
                                                value="${org.orgId}"><strong>X</strong></button>
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