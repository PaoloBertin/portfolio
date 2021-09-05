function redirectTo(portfolioId) {
    var value = portfolioId.value;
//    var url = 'http://localhost:8082/portfolios/' + value;
//             http://localhost:8082/portfolios/1
    var protocol = window.location.protocol;
    var hostname = window.location.hostname;
    var port     = window.location.port;
    var url      = protocol + '//' + hostname + ':' + port + '/portfolios/' + value;

    console.log('href     =' + window.location.href);
    console.log('protocol = ' + protocol);
    console.log('hostname =' + hostname);
    console.log('port =' + port);
    console.log('value=' + value);
    console.log('url=' + url);
//    location.href=document.getElementById("selectbox").value;
    location.href=url;

//    var selectedText = portfolioId.options[portfolioId.selectedIndex].innerHTML;
//    var selectedValue = portfolioId.value;
//    alert("Selected Text: " + selectedText + " Value: " + selectedValue);
}