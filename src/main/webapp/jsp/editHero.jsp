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
                        <li role="presentation"><a href="${pageContext.request.contextPath}/sighting"><strong>Report Sighting</strong></a></li>
                        <li role="presentation" class="active"><a href="${pageContext.request.contextPath}/hero">Heroes</a></li>
                        <li role="presentation"><a href="${pageContext.request.contextPath}/organization">Organizations</a></li>
                        <li role="presentation"><a href="${pageContext.request.contextPath}/location">Places</a></li>
                    </ul>
                </div>
            </div>

            <!-- Edit Form -->

            <div class="row">
                <div class="col-md-5">
                    <div>
                        <h3>Details : ${hero.name}</h3>
                    </div>
                    <sf:form role="form"
                             class="form-horizontal"
                             modelAttribute="hero"
                             action="doEdit"
                             method="POST">
                        <div class="form-group">
                            <label for="heroName">
                                Name
                            </label>
                            <sf:input type="text"
                                      class="form-control" 
                                      id="heroName"
                                      path="name"
                                      placeholder="Hero Name"/>
                            <sf:errors path="name"
                                       cssClass="btn btn-danger"/>
                        </div>
                        <div class="form-group">

                            <label for="heroDescription">
                                Description
                            </label>
                            <sf:input type="text"
                                      class="form-control"
                                      id="heroDescription"
                                      path="description"
                                      placeholder="Short description of this hero."/>
                            <sf:errors path="description"
                                       cssClass="btn btn-danger"/>
                        </div>
                        <div class="form-group">
                            <label for="superPower">
                                Superpower
                            </label>

                            <select name="superPower" class="form-control">
                                <c:forEach var="power" items="${powers}">
                                    <c:choose>
                                        <c:when test="${power.superPowerId == hero.power.superPowerId}">
                                            <option name="superPower" value="${power.superPowerId}" selected="selected">${power.name}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option name="superPower" value="${power.superPowerId}">${power.name}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                            <sf:hidden path="heroId"/>
                        </div>

                        <div class="row" style="text-align: right">
                            <button type="submit" class="btn btn-primary">
                                <strong>Update Hero</strong>
                            </button>
                        </div>
                    </sf:form>
                    <a href="/HeroTracker/hero"><u><< Heroes</u></a>
                </div>

                <!-- Relationship editing section.  -->

                <div class="col-md-7">
                    <div>
                        <h3>Known Affiliations</h3>
                        <br/>
                    </div>
                    <div class="col-md-12">
                        <c:if test="${empty memberships}">
                            <div class="row">
                                <span><strong>No known affiliations.</strong></span>
                            </div>
                        </c:if>
                        <c:forEach var="membership" items="${memberships}">
                            <div class="row">
                                <div class="col-md-2">
                                    <img class="heroThumb" height="50" width="50" alt="heroImg" src="https://s-media-cache-ak0.pinimg.com/736x/2a/23/55/2a2355413c128b319e04ae8f6ebe2a7f.jpg"/>
                                </div>
                                <div class="col-md-8">
                                    <a href="/HeroTracker/organization/editOrg?orgId=${membership.org.orgId}"><strong>${membership.org.name}</strong></a>
                                </div>
                                <div class="col-md-2">
                                    <form role="form" method="POST" action="/HeroTracker/organization/removeMember">
                                        <button type="submit"
                                                class="btn btn-danger"
                                                name="memberId"
                                                value="${membership.orgHeroId}"><strong>X</strong></button>
                                        <input type="hidden" name="heroId" value="${membership.hero.heroId}"/>
                                        <input type="hidden" name="origin" value="hero"/>
                                    </form>
                                </div>
                            </div>
                        </c:forEach>
                        <div class="row">
                            <div class="col-md-2">
                                <img class="newThumb" height="50" width="50" alt="newImg" src="http://orig04.deviantart.net/fc6f/f/2011/202/c/9/superman_icon_2_by_jeremymallin-d417prm.png"/>
                            </div>
                            <div class="col-md-8">
                                <form role="form" id="addMembership" method="POST" action="/HeroTracker/organization/addMember">
                                    <select name="orgId" class="form-control">
                                        <option selected>Add New...</option>
                                        <c:forEach var="org" items="${orgs}">
                                            <option value="${org.orgId}">${org.name}</option>
                                        </c:forEach>
                                    </select>
                                    <input type="hidden" name="heroId" value="${hero.heroId}"/>
                                    <input type="hidden" name="origin" value="hero"/>
                                </form>
                            </div>
                            <div class="col-md-2">
                                <button type="submit"
                                        form="addMembership"
                                        class="btn btn-success"><strong>+</strong></button>
                            </div>
                        </div>
                    </div>
                    <br/><br/><br/>
                    
                    <!-- Recent Sightings for Hero-->
                    
                    <div>
                        <h3>Recent ${hero.name} Sightings</h3>
                        <br/>
                    </div>
                    <div class="col-md-12">
                        <c:if test="${empty heroSightings}">
                            <div class="row">
                                <span><strong>No known sightings.</strong></span>
                            </div>
                        </c:if>
                        <c:forEach var="heroSighting" items="${heroSightings}">
                            <div class="row">
                                <div class="col-md-2">
                                    <img class="heroThumb" height="50" width="50" alt="heroImg" src="https://s-media-cache-ak0.pinimg.com/736x/2a/23/55/2a2355413c128b319e04ae8f6ebe2a7f.jpg"/>
                                </div>
                                <div class="col-md-8">
                                    <a href="/HeroTracker/sighting/editSighting?sightingId=${heroSighting.sighting.sightingId}">
                                        <strong>${heroSighting.sighting.dateOccurred} - 
                                            ${heroSighting.sighting.location.address.city}, ${heroSighting.sighting.location.address.state}</strong>
                                    </a>
                                </div>
                                <div class="col-md-2">
                                    <form role="form" method="POST" action="/HeroTracker/hero/deleteHeroSighting">
                                        <button type="submit"
                                                class="btn btn-danger"
                                                name="sightingId"
                                                value="${heroSighting.heroSightingId}"><strong>X</strong></button>
                                        <input type="hidden" name="heroId" value="${hero.heroId}"/>
                                        <input type="hidden" name="origin" value="hero"/>
                                    </form>
                                </div>
                            </div>
                        </c:forEach>
                        <div class="row">
                            <div class="col-md-2">
                                <img class="newThumb" height="50" width="50" alt="newImg" src="http://orig04.deviantart.net/fc6f/f/2011/202/c/9/superman_icon_2_by_jeremymallin-d417prm.png"/>
                            </div>
                            <div class="col-md-8">
                                <a href="/HeroTracker/sighting/reportSighting">
                                    <strong>Report Sighting of ${hero.name}</strong>
                                </a>
                            </div>
                            <div class="col-md-2">
                                <a href="/HeroTracker/sighting/reportSighting">
                                    <button type="button"
                                            class="btn btn-success"><strong>+</strong></button>
                                </a>
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