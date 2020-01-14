type 'a vehicle =
    { (* Capacity: how many products can be carried. *)
      capacity: int ;

      (* Products carried now by this vehicle. *)
      carry: 'a list ;

      (* History of previous deliveries : product & destination. 
       * The destination is just a code (an int). *)
      history: ('a * int) list }

;;


let  f1 g = let (a,b) = g() in
    [a,b]
;;

let rec f2 = function
  |[] -> []
  |[None] -> []
  |None::Some x:: xs -> f2 xs
  |None::xs -> f2 xs
  |Some x :: xs -> x :: f2 xs
;;



f2 [ Some 2 ; None ; None ; Some 8 ; Some 3 ] ;;

f2 [ Some 2 ; None ; None ; Some 8 ]  


let freespace elem = elem.capacity - List.length(elem.carry)
;;

let rec f3 vlist = match vlist with
  |[] -> failwith "not found"
  |x::xs -> let rec loop acu = function
              |[] -> acu
              |y::rest -> if freespace x > freespace y then loop x rest else loop y rest 
      in
        loop x xs
;;

let v1 = { capacity = 3 ; carry = [ 'a' ; 'b' ] ; history = [] } ;;    (* Its free space is 1 *) ;;
let v2 = { capacity = 3 ; carry = [ 'x' ] ; history = [] } ;;    (* Its free space is 2 *) ;;


f3 [ v1 ; v2 ];;



let f4 g v =
  let new_v = { capacity=v.capacity; carry = List.map g v.carry; history = List.map (fun (x,i) -> (g x, i) )  v.history }
  in
    new_v
;;


let f5 pred v =
  let (x,i) = List.split v.history
  in
  let xcarry = List.find_all pred v.carry and xhistory = List.find_all pred x
  in
    if (List.length xcarry) = 0 && (List.length xhistory)=0 then None else Some (List.hd xcarry)
;;

let is_xy c = (c = 'x' || c = 'y') ;;
let v1 = { capacity = 3 ; carry = [ 'a' ; 'b' ] ; history = [] } ;; 
let v2 = { capacity = 3 ; carry = [ 'x' ] ; history = [] } ;; 

f5 is_xy v2;;

let rec f6 v =
  let history = v.history 
  in
  let rec loop acu = function
    |[] -> acu
    |(x,i1) :: xs -> let rec loop2 = function
                       |[] -> []
                       |(y,i2) :: ys  -> if i1 = i2 then loop ((i1,[x;y]) :: acu) xs else loop acu ys
        in
          loop2 xs
  in
    loop [] history
;;


let v3 = { capacity = 2 ; carry = [] ; history = [ ('x', 7) ; ('y', 7) ; ('x', 3) ; ('x', 7) ] }
;;

f6 v3;;

(* ------------------2018------------------*)

let rec f1 g l =
  let rec loop acu l = match l with
    |[] -> acu
    |x::xs -> if g x = None then loop acu xs else loop (g x) xs
  in
    loop (None) l
;;

let g100 x = if x < 0 then None else Some (x > 100) ;;

f1 g100 [ -4 ; -3 ; -104 ] ;;

f1 g100 [ -4 ; 104 ; 3 ] ;;

f1 g100 [ -4 ; 3 ; 104 ] ;;


let rec f2 la lb = match la, lb with
  |[],[] -> []
  |x::xs::xss, y::ys::yss -> (x+y) :: (xs+ys) ::  f2 xss (y::ys::yss)
  |_ -> []

;;

f2 [] [ 0 ; 1 ] ;;

f2 [ 6 ; 7 ; 8 ; 9 ] [ 0 ; 1 ] ;;

f2 [ 6 ; 7 ]  [ 0 ; 1 ]    ;;
f2 [ 6 ; 7 ; 8 ; 9 ] [ 2 ; 0 ];;


(*** These types represent the scores of some players playing the game Starcrap II. ***)

(* Do not modify this file (changes here will be ignored by your main program). *)


(* Players can be human, robots, or Zeus, which has superpowers. 
 * Human and Droids have a name (string). *)
type player = Human of string | Droid of string | Zeus

(* Player information. The type parameter 'a stands for the type of objects collected by the player. *)
;;
type 'a info =
    {
      player: player ;

      (* Indicates if this player is the team leader. *)
      leader: bool ;

      (* Score 1 reflects the number of units built by the player. *)
      score1: int ;

      (* Score 2 reflects the number of structures built by the player. *)
      score2: int ;

      (* Objects collected by the player. *)
      collect: 'a list ;
    }

(* A team is a list of player infos. *)
type 'a team = 'a info list


(*******  You do not need the following types and exception before questions 5, 6, and 7 (and may be, you do not need them at all).  **************)

type result = Greatereq | Smaller

(* A tree. Each node contains a player info or is empty.
 * A non-empty node N may have two subtrees:
 *    Subtree 1 : nodes that are smaller than N.
 *    Subtree 2 : nodes that are greater than or equal to N.
*)
type 'a tree =
    | Empty
    | Node of 'a node

and 'a node =
    { info: 'a info ;
      sub1: 'a tree ;
      sub2: 'a tree }

exception Uncomparable


(**************************************************)


(* 
* In order to test, you may use the following predefined values... or create your own in your exam file. 
*
* DO NOT MODIFY THIS FILE. It will not be taken into account by your main program.
*)

let zeus = { player = Zeus ; leader = false ; score1 = 20 ; score2 = 80 ; collect = [ "Kyber cristal" ; "Yoda mug" ] }

let komp = { player = Human "Komp" ; leader = false ; score1 = 80 ; score2 = 20 ; collect = [] }
let comp = { player = Human "Komp" ; leader = true  ; score1 = 20 ; score2 = 30 ; collect = [ "Triforce" ] }
let domp = { komp with player = Droid "Komp" }           

let alat = { player = Droid "Alat" ; leader = true ; score1 = 40 ; score2 = 120 ; collect = [ "Excalibur" ; "Tesseract" ; "Death Star plans"] }
let dlat = { player = Droid "Alat" ; leader = false ; score1 = 80 ; score2 = 20 ; collect = [] }

let lubt = { player = Human "Lubt" ; leader = false ; score1 = 60 ; score2 = 10 ; collect = [ "Yu-Gi-Oh deck" ; "Millenium puzzle" ; "Triforce" ] }
let ladk = { player = Droid "Ladk" ; leader = true ; score1 = 50 ; score2 = 100 ; collect = [ "Internet Explorer" ; "Laisser-Passer A38"] }

let team1 = [ zeus ; komp ; alat ]
let team2 = [ lubt ; ladk ]
let team3 = [ zeus ; lubt ; ladk ]
let team4 = [ comp ; lubt ; ladk ]
let team5 = [ komp ; dlat ; lubt ]
let team6 = [ comp ]
let team7 = [ komp ]
let team8 = [ lubt ; comp ]
let all   = [ komp ; zeus ; comp ; domp ; dlat ; alat ; lubt ; ladk ]



let f3 t = 
  let rec loop acu t = match t with
    |[] -> acu
    |{leader = false } :: xs -> loop acu xs
    |{leader = true}::xs -> if (acu) then false else loop true xs
  in
    loop false t

;;

f3 team1;;

f3 team2 ;;
f3 team5;;
f3 [];;

f3 all;;

let rec new_collect f c = 
  List.map f c
;;

let new_x f x = { x with collect = new_collect f x.collect }

let rec f4 f t = match t with
  |[] -> []
  |x::xs ->  new_x f x :: f4 f xs
;;


let fst_letter s = s.[0]
;;
f4 String.length team2 ;;


let rec f5 i1 i2 = match i1, i2 with
  |{score1 = scx1;score2=scx2},{score1 = scy1; score2 = scy2 } -> if (scx1 >= scy1) && (scx2 >= scy2) then Greatereq else Smaller 
;;
f5 zeus zeus
;;
