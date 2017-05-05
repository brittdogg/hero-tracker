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
                        <li role="presentation"><a href="${pageContext.request.contextPath}/hero">Heroes</a></li>
                        <li role="presentation" class="active"><a href="${pageContext.request.contextPath}/organization">Organizations</a></li>
                        <li role="presentation"><a href="${pageContext.request.contextPath}/location">Places</a></li>
                    </ul>
                </div>
            </div>

            <!-- Edit Form -->

            <div class="row">
                <div class="col-md-5">
                    <div>
                        <h3>Organization Details : ${org.name}</h3>
                    </div>
                    <sf:form role="form"
                             class="form-horizontal"
                             modelAttribute="org"
                             action="doEdit"
                             method="POST">
                        <div class="form-group">
                            <label for="orgName">
                                Name
                            </label>
                            <sf:input type="text"
                                      class="form-control" 
                                      id="orgName"
                                      path="name"
                                      placeholder="Organization Name"/>
                            <sf:errors path="name"
                                       cssClass="btn btn-danger"/>
                        </div>
                        <div class="form-group">

                            <label for="orgDescription">
                                Description
                            </label>
                            <sf:input type="text"
                                      class="form-control"
                                      id="orgDescription"
                                      path="description"
                                      placeholder="Short description of this organization."/>
                            <sf:errors path="description"
                                       cssClass="btn btn-danger"/>
                        </div>
                        <div class="form-group">

                            <label for="orgLocation">
                                Headquarters
                            </label>
                            <select class="form-control" name="addressId">
                                <c:forEach var="loc" items="${locations}">
                                    <c:choose>
                                        <c:when test="${org.address.addressId == loc.address.addressId}">
                                            <option name="addressId" value="${loc.address.addressId}" selected>${loc.name} - ${loc.address.city}, ${loc.address.state}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option name="addressId" value="${loc.address.addressId}">${loc.name} - ${loc.address.city}, ${loc.address.state}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">

                            <label for="orgContact">
                                Contact Name
                            </label>
                            <sf:input type="text"
                                      class="form-control"
                                      id="orgContact"
                                      path="contactName"
                                      placeholder="Full name of contact"/>
                            <sf:errors path="contactName"
                                       cssClass="btn btn-danger"/>
                        </div>
                        <div class="form-group">

                            <label for="orgPhone">
                                Contact Phone
                            </label>
                            <sf:input type="text"
                                      class="form-control"
                                      id="orgPhone"
                                      path="contactPhone"
                                      placeholder="123-456-7890"/>
                            <sf:errors path="contactPhone"
                                       cssClass="btn btn-danger"/>
                        </div>
                        <div class="form-group">

                            <label for="orgEmail">
                                Contact Email
                            </label>
                            <sf:input type="text"
                                      class="form-control"
                                      id="orgEmail"
                                      path="contactEmail"
                                      placeholder="contact@example.com"/>
                            <sf:errors path="contactEmail"
                                       cssClass="btn btn-danger"/>
                        </div>

                        <sf:hidden path="orgId"/>

                        <div class="row" style="text-align: right">
                            <button type="submit" class="btn btn-primary">
                                <strong>Update Organization</strong>
                            </button>
                        </div>
                    </sf:form>
                    <a href="/HeroTracker/organization"><u><< Organizations</u></a>
                    <br/><br/>
                </div>

                <!-- Relationship editing section.  -->

                <div class="col-md-7">
                    <div>
                        <h3>Known ${org.name} Members</h3>
                        <br/>
                    </div>
                    <div class="col-md-12">
                        <c:if test="${empty members}">
                            <div class="row">
                                <span><strong>No known members.</strong></span>
                            </div>
                        </c:if>
                        <c:forEach var="member" items="${members}">
                            <div class="row">
                                <div class="col-md-2">
                                    <img class="heroThumb" height="50" width="50" alt="heroImg" src="https://s-media-cache-ak0.pinimg.com/736x/2a/23/55/2a2355413c128b319e04ae8f6ebe2a7f.jpg"/>
                                </div>
                                <div class="col-md-8">
                                    <a href="/HeroTracker/hero/editHero?heroId=${member.hero.heroId}"><strong>${member.hero.name}</strong></a>
                                </div>
                                <div class="col-md-2">
                                    <form role="form" method="POST" action="/HeroTracker/organization/removeMember">
                                        <button type="submit"
                                                class="btn btn-danger"
                                                name="memberId"
                                                value="${member.orgHeroId}"><strong>X</strong></button>
                                                <input type="hidden" name="orgId" value="${org.orgId}"/>
                                                <input type="hidden" name="origin" value="org"/>
                                    </form>
                                </div>
                            </div>
                        </c:forEach>
                        <div class="row">
                            <div class="col-md-2">
                                <img class="newThumb" height="50" width="50" alt="newImg" src="http://orig04.deviantart.net/fc6f/f/2011/202/c/9/superman_icon_2_by_jeremymallin-d417prm.png"/>
                            </div>
                            <div class="col-md-8">
                                <form role="form" id="addMember" method="POST" action="/HeroTracker/organization/addMember">
                                    <select name="heroId" class="form-control">
                                        <option selected>Add New...</option>
                                        <c:forEach var="hero" items="${heroes}">
                                            <option value="${hero.heroId}">${hero.name}</option>
                                        </c:forEach>
                                    </select>
                                    <input type="hidden" name="orgId" value="${org.orgId}"/>
                                    <input type="hidden" name="origin" value="org"/>
                                </form>
                            </div>
                            <div class="col-md-2">
                                    <button type="submit"
                                            form="addMember"
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