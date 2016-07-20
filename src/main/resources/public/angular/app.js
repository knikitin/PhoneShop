( function(){

var app = angular.module('app', ['ngRoute']);


app.config(['$routeProvider', function($routeProvider) {

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
    .otherwise({
      redirectTo:'/'
    });
}])

app.controller('PhoneListController', [ '$http', '$routeParams', '$location', function($http, $routeParams, $location) {
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
          //phonesList.phones = getPhonesList(phonesList.phonesOnList);
      }
  })

  var generateNavEntity = function(pageId, captionIn, classId){
       return {caption: captionIn, link : '#catalog/page-'+pageId+"?phonesCount="+phonesList.phonesOnList, class: classId}
  }

  $http.get("http://localhost:8080/phoneslist/page"+Number(phonesList.pageId)+"for"+Number(phonesList.phonesOnList)).success(function(data){
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
     return "/img/"+id.toString(16)+".jpg";
  };


}])

})();