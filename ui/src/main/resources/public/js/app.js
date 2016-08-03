angular.module('app', [ 'ngRoute' ])
.config(function($routeProvider, $httpProvider) {

  $routeProvider
    .when('/', {
      redirectTo:'/catalog'
    })
    .when('/catalog', {
      redirectTo:'/catalog/page-1'
    })
    .when('/catalog/page-:pageId', {
      controller:'PhoneListController as phonesList',
      templateUrl:'angular/pagephoneslist.html',
    })
    .when('/phone/add', {
      controller:'PhoneAddController as phone',
      templateUrl:'angular/pageaddphone.html',
    })
    .when('/phone/:phoneId/edit', {
      controller:'PhoneEditController as phone',
      templateUrl:'angular/pageeditphone.html',
    })
    .when('/phone/:phoneId', {
      controller:'PhoneViewController as phone',
      templateUrl:'angular/pagephone.html',
    })
    .otherwise({
      redirectTo:'/'
    });

	$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
	$httpProvider.defaults.headers.common['Accept'] = 'application/json';

})
.controller('navigation',
    function($rootScope, $http, $location, $route) {

	var self = this;

    function setAutentication(authenticated, name, authorities){
        $rootScope.authenticated = authenticated;
        $rootScope.name = name;
        $rootScope.authorities = authorities;
        $rootScope.checkRoles = function (authorities, role){
            for(i in authorities){
                if (authorities[i].authority === role){
                    return true;
                }
            }
            return false;
        };

    }

	$http.get('user').then(function(response) {
 		if (response.data.name) {
		    setAutentication(true, response.data.name, response.data.userAuthentication.authorities)
		} else {
		    setAutentication(false, "", {})
		}
	}, function() {
        setAutentication(false, "", {})
	});

	self.credentials = {};

	self.logout = function() {
		$http.post('logout', {}).finally(function() {
			setAutentication(false, "", {})
			$location.path("/");
		});
	}
})
.controller('PhoneListController', [ '$http', '$routeParams', '$location', function($http, $routeParams, $location) {
  var phonesList = this;

  phonesList.$routeParams = $routeParams;
  phonesList.pageId = !isNaN(phonesList.$routeParams["pageId"]) && (phonesList.$routeParams["pageId"]>0) && phonesList.$routeParams["pageId"]||1;
  phonesList.phonesOnList = !isNaN(phonesList.$routeParams["phonesCount"]) && (phonesList.$routeParams["phonesCount"]>1) && phonesList.$routeParams["phonesCount"]|| localStorage.phonesOnList ||"20";

  phonesList.phones = [];
  phonesList.navigation = [];

  Object.defineProperty(phonesList, "phonesOnListWithAct", {
      get: function() {
        return phonesList.phonesOnList;
      },
      set: function(value) {
          newPage = parseInt( ((phonesList.phonesOnList * phonesList.pageId ) - phonesList.phonesOnList + 1 ) / value ) + 1;
          phonesList.phonesOnList = value;
          localStorage.phonesOnList = phonesList.phonesOnList;
          $location.url("/catalog/page-"+newPage+"?phonesCount="+phonesList.phonesOnList);
      }
  })

  var generateNavEntity = function(pageId, captionIn, classId){
       return {caption: captionIn, link : '#catalog/page-'+pageId+"?phonesCount="+phonesList.phonesOnList, class: classId}
  }

  $http.get("http://localhost:8080/resource/phoneslist/page"+Number(phonesList.pageId)+"for"+Number(phonesList.phonesOnList)).success(function(data){
        phonesList.phones = data.phonesList;
        phonesList.pageCount = data.pageCount;

        startPage = Number(phonesList.pageId) - 2;
        if (startPage > 1) { phonesList.navigation.push(generateNavEntity(1,"<<")) };
        if (startPage > 2) { phonesList.navigation.push(generateNavEntity(startPage-1,"<")) };
        for (i = startPage; i < startPage + 5; i++){
            if ((i > 0) && (i < phonesList.pageCount + 1)) {
               phonesList.navigation.push(generateNavEntity(i,i,i==Number(phonesList.pageId)? "active" : ""));
            };
        }

        if (startPage + 4 < phonesList.pageCount) { phonesList.navigation.push(generateNavEntity(startPage + 5,">")) };
        if (startPage + 5 < phonesList.pageCount) { phonesList.navigation.push(generateNavEntity(phonesList.pageCount, ">>")) };

      });

  phonesList.getImgUrl = function(id){
     return "/resource/img/"+id.toString(16)+".jpg";
  };


}])
.controller('PhoneViewController', [ '$http', '$routeParams', function($http, $routeParams) {
  var phone = this;

  phone.$routeParams = $routeParams;
  var i = phone.$routeParams["phoneId"];
  phone.phoneId = !isNaN(i) && (i>0) && i||1;

  phone.phone = {};

  $http.get("http://localhost:8080/resource/phones/"+Number(phone.phoneId)).success(function(data){
        phone.phone = data;

      });

  phone.getImgUrl = function(id){
     return (id)?"/resource/img/"+id.toString(16)+".jpg":"";
  };


}])
.controller('PhoneEditController', [ '$http', '$routeParams', function($http, $routeParams) {
  var phone = this;

  phone.$routeParams = $routeParams;
  var i = phone.$routeParams["phoneId"];
  phone.phone = {};

  phone.phoneId = !isNaN(i) && (i>0) && i||1;
  $http.get("http://localhost:8080/resource/phones/"+Number(phone.phoneId)).success(function(data){
        phone.phone = data;
      });

  phone.getImgUrl = function(id){
     return (id)?"/resource/img/"+id.toString(16)+".jpg":"";
  };


}])
.controller('PhoneAddController', [ '$http', '$location', function($http, $location) {
  var phone = this;
  var stateAdd = true;
  phone.phone = {};
  phone.phone.model = "";
  phone.phone.brand = "";

  phone.add = function() {
    if (stateAdd){
        if ((phone.phone.model.length>0) && (phone.phone.brand.length>0)){
           $http.post('http://localhost:8080/resource/phones', phone.phone).then(
                function(response){//вызвать переход в редактирование этого телефона
                    $location.url("/phone/"+response.data.id+"/edit");
                },
                function(){
                    alert("An error occurred when adding the phone. Try again.(Error with status: " + response.status +")")
                });
           stateAdd = false;
        } else
        { alert("Error in data. All field must be filled")
        }
    }
  }

  phone.cancel = function() {
    $location.path("/");
  }

}])
;