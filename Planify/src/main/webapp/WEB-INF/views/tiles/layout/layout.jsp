<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <tiles:insertAttribute name="hdr"/>
    <title>Planify</title>
</head>
<body>
    <div id="wrapper">
		<tiles:insertAttribute name="left"/>
		<!-- Content Wrapper -->
        <div id="content-wrapper" class="d-flex flex-column">
            <!-- Main Content -->
            <div id="content">
    			<tiles:insertAttribute name="header"/>
    			
                <div class="container-fluid">
    				<tiles:insertAttribute name="body"/>
                </div>
		
		    </div>
		    <tiles:insertAttribute name="footer"/>
		</div>
    </div>
</body>
</html>
