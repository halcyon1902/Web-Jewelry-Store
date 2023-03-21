<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Support 4TV</title>

<link href="/resources/css/bootstrap.css" rel="stylesheet"
	type="text/css" media="all" />
<link href="/resources/css/style.css" rel="stylesheet" type="text/css"
	media="all" />
<!-- js -->
<script src="/resources/js/jquery.min.js"></script>
<!-- //js -->
<link rel="stylesheet" type="text/css"
	href="/resources/css/jquery-ui.css">
<!-- for bootstrap working -->
<script type="text/javascript"
	src="/resources/js/bootstrap-3.1.1.min.js"></script>
<!-- //for bootstrap working -->
<link
	href='//fonts.googleapis.com/css?family=Open+Sans:400,300,300italic,400italic,600,600italic,700,700italic,800,800italic'
	rel='stylesheet' type='text/css'>
<link
	href='//fonts.googleapis.com/css?family=Lato:400,100,100italic,300,300italic,400italic,700,700italic,900,900italic'
	rel='stylesheet' type='text/css'>
<!-- animation-effect -->
<link href="/resources/css/animate.min.css" rel="stylesheet">
<script src="/resources/js/wow.min.js"></script>
<script>
	new WOW().init();
</script>
<!-- //animation-effect -->
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>

	<div class="container">
		<h3>Liên Hệ Với Chúng Tôi</h3>
		<p class="est"></p>
		<div class="mail-grids">
			<div class="col-md-8 mail-grid-left animated wow slideInLeft"
				data-wow-delay=".5s">
				<form>
					<input type="text" value="Name" onfocus="this.value = '';"
						onblur="if (this.value == '') {this.value = 'Name';}" required="">
					<input type="email" value="Email" onfocus="this.value = '';"
						onblur="if (this.value == '') {this.value = 'Email';}" required="">
					<input type="text" value="Subject" onfocus="this.value = '';"
						onblur="if (this.value == '') {this.value = 'Subject';}"
						required="">
					<textarea type="text" onfocus="this.value = '';"
						onblur="if (this.value == '') {this.value = 'Message...';}"
						required="">Nhắn Tin.....</textarea>
					<input type="submit" value="Xác Nhận">
				</form>
			</div>
			<div class="col-md-4 mail-grid-right animated wow slideInRight"
				data-wow-delay=".5s">
				<div class="mail-grid-right1">

					<h4>4TV SUPORT TEAM</h4>
					<ul class="phone-mail">
						<li><i class="glyphicon glyphicon-earphone"
							aria-hidden="true"></i>Phone: (028) 5445 7777 - Fax: (028) 5445
							4444</li>
						<li><i class="glyphicon glyphicon-envelope"
							aria-hidden="true"></i>Email: <a href="mailto:info@example.com">
								hutech@hutech.edu.vn</a></li>
					</ul>
					<ul class="social-icons">
						<li><a href="#" class="facebook"></a></li>
						<li><a href="#" class="twitter"></a></li>
						<li><a href="#" class="g"></a></li>
						<li><a href="#" class="instagram"></a></li>
					</ul>
				</div>
			</div>
			<div class="clearfix"></div>
		</div>
		<iframe
			src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3918.4205948965932!2d106.78291401531642!3d10.855580060694873!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3175276e7ea103df%3A0xb6cf10bb7d719327!2zSHV0ZWNoIEtodSBFIC0gVHJ1bmcgVMOibSDEkMOgbyBU4bqhbyBOaMOibiBM4buxYyBDaOG6pXQgTMaw4bujbmcgQ2Fv!5e0!3m2!1svi!2s!4v1678808743080!5m2!1svi!2s"
			width="100%" height="650" style="border: 0;" allowfullscreen=""
			loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
