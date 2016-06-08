<div class="navbar navbar-inverse">
	<div class="navbar-inner">
		<div class="container-fluid">
			<a class="brand">
				<small>
					<i class="icon-leaf"></i> ${pd.SYSNAME}
				</small> 
			</a>
		  	<ul class="nav ace-nav pull-right">
				<li style="background:none repeat scroll 0 0 #2c6aa0;color:#fff">
					版本：v-1.0.0
				</li>
				<li class="light-blue user-profile">
					<a class="user-menu dropdown-toggle" href="javascript:;" data-toggle="dropdown">
						<img alt="FH" src="static/avatars/user.jpg" class="nav-user-photo" />
						<span id="user_info">
						</span>
						<i class="icon-caret-down"></i>
					</a>
					<ul id="user_menu" class="pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-closer">
						<li><a onclick="editUserH();" style="cursor:pointer;"><i class="icon-user"></i> 修改资料</a></li>
						<li class="divider"></li>
						<li><a href="logout"><i class="icon-off"></i> 退出</a></li>
					</ul>
				</li>
		  	</ul><!--/.ace-nav-->
		</div><!--/.container-fluid-->
	</div><!--/.navbar-inner-->
</div><!--/.navbar-->
<!--引入属于此页面的js -->
<script type="text/javascript" src="static/js/myjs/head.js"></script>
