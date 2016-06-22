<%
	String pathl = request.getContextPath();
	String basePathl = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+pathl+"/";
%>
<!-- 本页面涉及的js函数，都在head.jsp页面中     -->
<div id="sidebar" class="menu-min">
	<div id="sidebar-shortcuts">
	</div><!-- #sidebar-shortcuts -->
	<ul class="nav nav-list">
		<li class="active" id="fhindex">
			<a href="main/index"><i class="icon-dashboard"></i><span>系统首页</span></a>
		</li>
		<c:if test="${!empty menuList}">
			<c:forEach items="${menuList}" var="menu">
				<li id="lm${menu.menu_id }">
					<a style="cursor:pointer;" class="dropdown-toggle" >
						<i class="${menu.menu_icon == null ? 'icon-desktop' : menu.menu_icon}"></i>
						<span>${menu.menu_name }</span>
						<b class="arrow icon-angle-down"></b>
					</a>
					<ul class="submenu">
						<c:if test="${!empty menu.subMenu}">
							<c:forEach items="${menu.subMenu}" var="sub">
								<c:choose>
									<c:when test="${not empty sub.menu_url}">
										<li id="z${sub.menu_id }">
										<a style="cursor:pointer;" target="mainFrame"  onclick="siMenu('z${sub.menu_id }','lm${menu.menu_id }','${sub.menu_name }','${sub.menu_url }')"><i class="icon-double-angle-right"></i>${sub.menu_name }</a></li>
									</c:when>
									<c:otherwise>
										<li>
											<a href="javascript:void(0);">
												<i class="icon-double-angle-right"></i>${sub.menu_name }
											</a>
										</li>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:if>
					</ul>
				</li>
			</c:forEach>
		</c:if>
	</ul><!--/.nav-list-->
	<div id="sidebar-collapse"><i class="icon-double-angle-left"></i></div>
</div><!--/#sidebar-->

