<%@ include file="/WEB-INF/views/include/header.html" %>
<body>
<div class="container" style="margin-top: 100px;">
	<div class="col-md-4 col-md-offset-4">
		<span><img src="/KpcPersonalEvalation/images/company_logo.png" /></span>
		<div class="panel panel-default">
		    <div class="panel-heading">
				<h3 class="panel-title">Please sign in</h3>
		    </div>
		    <div class="panel-body">
				<form class="form-signin" method="POST">
					<fieldset>
					<div class="form-group">
						<input type="text" name="employeID" class="form-control" placeholder="EmployeID" required="required" autofocus="autofocus" />
					</div>
					<div class="form-group">
						<input type="password" name="password" class="form-control" placeholder="Password" value="" required="required" />
					</div>
					<div class="checkbox">
			            <label>
			              <input name="remember" type="checkbox" value="Remember Me" /> Remember Mes
			            </label>
					</div>
					<button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
					</fieldset>
				</form>
			</div>
		</div>
	</div>
</div>
</body>
</html>