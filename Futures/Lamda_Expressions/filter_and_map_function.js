//pass a function to a map
//map is a higher order function that accepts a lambda function as argument
const array1 = [1,2,3,4,5,6,7,8];

const map1 = array1.filter(n=> n%2==0).map(n=> n*2);

console.log(map1);
