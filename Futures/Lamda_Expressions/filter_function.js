//pass a function to a filter
//filter is a higher order function that accepts function is_even as aan argument
const array1 = [1,2,3,4,5,6,7,8];
function is_even(n){ return n%2 ==0;}
//function is_odd(n){ return n%2 !=0;}
const filter1 = array1.filter(is_even);
//const filter1 = array1.filter(is_odd);

console.log(filter1);
