<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<head>
	<%@include file="/common/common.jsp"%>
	<script type="text/javascript" src="<%=basePath%>/static/js/gateway/prepay.js"></script>
</head>
<body>
	<div class="row-fluid">
	<div class="span12">
		<div class="widget-box">
			<div class="widget-header widget-header-blue widget-header-flat wi1dget-header-large">
				<h4 class="lighter">富仁移动支付</h4>
				<div class="widget-toolbar">
					<label>
						<small class="green"><b>Validation</b></small>
						<input id="skip-validation" type="checkbox" class="ace-switch ace-switch-4" /><span class="lbl"></span>
					</label>
				</div>
			</div>
			<div class="widget-body">
			 <div class="widget-main">
				
				<div class="row-fluid">
					<div id="fuelux-wizard" class="row-fluid hidden">
					  <ul class="wizard-steps">
						<li data-target="#step1" class="active"><span class="step">1</span> <span class="title">Validation states</span></li>
						<li data-target="#step2"><span class="step">2</span> <span class="title">Alerts</span></li>
						<li data-target="#step3"><span class="step">3</span> <span class="title">Payment Info</span></li>
						<li data-target="#step4"><span class="step">4</span> <span class="title">Other Info</span></li>
					  </ul>
					</div>
					
					<hr />
					
					<div class="step-content row-fluid position-relative">
						<div class="step-pane active" id="step1">
							<h3 class="lighter block green">选择站点</h3>
							
							<form class="form-horizontal" id="sample-form">
								<div class="control-group warning">
									<label class="control-label" for="inputWarning">Input with warning</label>
									<div class="controls">
										<span class="span6 input-icon input-icon-right">
											<input class="span12" type="text" id="inputWarning" />
											<i class="icon-warning-sign"></i>
										</span>
										<span class="help-inline">Something may have gone wrong</span>
									</div>
								</div>
								<div class="control-group error">
									<label class="control-label" for="inputError">Error with tooltip</label>
									<div class="controls">
										<span class="span6 input-icon input-icon-right">
											<input class="tooltip-error span12" type="text" id="inputError" data-rel="tooltip" title="Error info!" data-trigger="focus" />
											<i class="icon-remove-sign"></i>
										</span>
										<span class="help-inline">Please correct the error</span>
									</div>
								</div>
								<div class="control-group success">
									<label class="control-label" for="inputSuccess">Input with success</label>
									<div class="controls">
										<span class="span6 input-icon input-icon-right">
											<input class="span12" type="text" id="inputSuccess" />
											<i class="icon-ok-sign"></i>
										</span>	
										<span class="help-inline">Woohoo!</span>
									</div>
								</div>
								<div class="control-group info">
									<label class="control-label" for="inputInfo">Blue Input with Info</label>
									<div class="controls">
										<span class="span6 input-icon input-icon-right">
											<input class="span12" type="text" id="inputInfo" />
											<i class="icon-info-sign"></i>
										</span>	
										<span class="help-inline">Info tip help!</span>
									</div>
								</div>
							</form>
							
							
							
							<form class="form-horizontal" id="validation-form" method="get">
							  
								<div class="control-group">
									<label class="control-label" for="email">Email Address:</label>
									<div class="controls">
										<div class="span12">
											<input type="email" name="email" id="email" class="span6" />
										</div>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="password">Password:</label>
									<div class="controls">
										<div class="span12">
											<input type="password" name="password" id="password" class="span4" />
										</div>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="password2">Confirm Password:</label>
									<div class="controls">
										<div class="span12">
											<input type="password" name="password2" id="password2" class="span4" />
										</div>
									</div>
								</div>
								
								<div class="hr hr-dotted"></div>
								
								<div class="control-group">
									<label class="control-label" for="name">Company Name:</label>
									<div class="controls">
										<span class="span12">
											<input class="span6" type="text" id="name" name="name" />
										</span>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="phone">Phone Number:</label>
									<div class="controls">
										<div class="span3 input-prepend">
											<span class="add-on"><i class="icon-phone"></i></span>
											<input class="span12" type="tel" id="phone" name="phone" />
										</div>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="url">Company URL:</label>
									<div class="controls">
										<div class="span12">
											<input type="url" id="url" name="url" class="span8" />
										</div>
									</div>
								</div>
								
								<div class="hr hr-dotted"></div>
								
								<div class="control-group">
									<label class="control-label">Subscribe to</label>
									<div class="controls">
										<span class="span12">
											<label><input name="subscription" value="1" type="checkbox" /><span class="lbl"> Latest news and announcements</span></label>
											<label><input name="subscription" value="2" type="checkbox" /><span class="lbl"> Product offers and discounts</span></label>
										</span>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">Gender</label>
									<div class="controls">
										<span class="span12">
											<label class="blue"><input name="gender" value="1" type="radio" /><span class="lbl"> Male</span></label>
											<label class="blue"><input name="gender" value="2" type="radio" /><span class="lbl"> Female</span></label>
										</span>
									</div>
								</div>
								
								<div class="hr hr-dotted"></div>
								
								<div class="control-group">
									<label class="control-label" for="state">State</label>
									<div class="controls">
										<span class="span12">
											<select id="state" name="state" class="chzn-select" data-placeholder="Click to Choose...">
												<option value=""></option>
												<option value="AL">Alabama</option><option value="AK">Alaska</option><option value="AZ">Arizona</option><option value="AR">Arkansas</option><option value="CA">California</option><option value="CO">Colorado</option><option value="CT">Connecticut</option><option value="DE">Delaware</option><option value="FL">Florida</option><option value="GA">Georgia</option><option value="HI">Hawaii</option><option value="ID">Idaho</option><option value="IL">Illinois</option><option value="IN">Indiana</option><option value="IA">Iowa</option><option value="KS">Kansas</option><option value="KY">Kentucky</option><option value="LA">Louisiana</option><option value="ME">Maine</option><option value="MD">Maryland</option><option value="MA">Massachusetts</option><option value="MI">Michigan</option><option value="MN">Minnesota</option><option value="MS">Mississippi</option><option value="MO">Missouri</option><option value="MT">Montana</option><option value="NE">Nebraska</option><option value="NV">Nevada</option><option value="NH">New Hampshire</option><option value="NJ">New Jersey</option><option value="NM">New Mexico</option><option value="NY">New York</option><option value="NC">North Carolina</option><option value="ND">North Dakota</option><option value="OH">Ohio</option><option value="OK">Oklahoma</option><option value="OR">Oregon</option><option value="PA">Pennsylvania</option><option value="RI">Rhode Island</option><option value="SC">South Carolina</option><option value="SD">South Dakota</option><option value="TN">Tennessee</option><option value="TX">Texas</option><option value="UT">Utah</option><option value="VT">Vermont</option><option value="VA">Virginia</option><option value="WA">Washington</option><option value="WV">West Virginia</option><option value="WI">Wisconsin</option><option value="WY">Wyoming</option>
											</select>
										</span>
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label" for="platform">Platform</label>
									<div class="controls">
										<span class="span12">
											<select class="span3" id="platform" name="platform">
												<option value="">------------------</option>
												<option value="linux">Linux</option>
												<option value="windows">Windows</option>
												<option value="mac">Mac OS</option>
												<option value="ios">iOS</option>
												<option value="android">Android</option>
											</select>
										</span>
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label" for="comment">Comment</label>
									<div class="controls">
										<span class="span12">
											<textarea class="span8" name="comment" id="comment"></textarea>
										</span>
									</div>
								</div>
								
								<div class="control-group">
									<div class="controls">
										<span class="span6">
											<label><input name="agree" id="agree" type="checkbox" /><span class="lbl"> I accept the policy</span></label>
										</span>
									</div>
								</div>
							  </form>
							
							
							
						</div>
						
						<div class="step-pane" id="step2">
							<div class="row-fluid">
								<div class="alert alert-success">
									<button type="button" class="close" data-dismiss="alert"><i class="icon-remove"></i></button>
									<strong><i class="icon-ok"></i> Well done!</strong>
									You successfully read this important alert message. <br />
								</div>
								<div class="alert alert-error">
									<button type="button" class="close" data-dismiss="alert"><i class="icon-remove"></i></button>
									<strong><i class="icon-remove"></i> Oh snap!</strong>
									Change a few things up and try submitting again. <br />
								</div>
								<div class="alert alert-warning">
									<button type="button" class="close" data-dismiss="alert"><i class="icon-remove"></i></button>
									<strong>Warning!</strong>
									Best check yo self, you're not looking too good. <br />
								</div>
								<div class="alert alert-info">
									<button type="button" class="close" data-dismiss="alert"><i class="icon-remove"></i></button>
									<strong>Heads up!</strong>
									This alert needs your attention, but it's not super important. <br />
								</div>
							</div>
						</div>
						
						<div class="step-pane" id="step3">
							<div class="center">
								<h3 class="blue lighter">This is step 3</h3>
							</div>
						</div>
						<div class="step-pane" id="step4">
							<div class="center">
								<h3 class="green">Congrats!</h3>
								Your product is ready to ship! Click finish to continue!
							</div>
						</div>
					</div>
					
					<hr />
					
					<div class="row-fluid wizard-actions">
						<button class="btn btn-prev"><i class="icon-arrow-left"></i> Prev</button>
						<button class="btn btn-success btn-next" data-last="Finish ">Next <i class="icon-arrow-right icon-on-right"></i></button>
					</div>
				</div>
			 </div><!--/widget-main-->
			</div><!--/widget-body-->
		</div>
	</div>
</div>
		
</body>
</html>

