fun main() {
    val list = LinkedList<Int>()

    list.add(1)
    list.add(2)
    list.add(3)

    list.go()
    println("IDEBFIDABFIBADIBFi")
    list.remove(3)
    println("IDEBFIDABFIBADIBFi")
    list.go()





    val add = { a: Int, b: Int -> a + b }
    println(add(2, 3)) // 5
    println(add(2, 0))





     val b : (Int)-> Unit ={ x -> println(x) }

    dosome(b)


    val headNode = list.head
    if (headNode != null) {
        list.some({ node -> println("Knoten-Daten: ${node.data}") }, headNode)
    }








}


class LinkedList<T> {

    class Node<T> {
        var data: T? = null
        var next: Node<T>? = null
    }

    var head: Node<T>? = null

    fun add(d: T) {
        val newNode = Node<T>()
        newNode.data = d

        if (head == null) {
            head = newNode
            return
        }

        var current = head!!
        while (current.next != null) {
            current = current.next!!
        }

        current.next = newNode
    }










    fun remove(d: T) {

        var prev = head
        var run = head?.next

        while (run != null) {
            if (run.data == d) {
                prev?.next = run.next
                return
            }

            prev = run
            run = run.next
        }
    }

    fun go() {


        var run = head

        while (run != null) {
            print(run.data)

            run = run.next
        }


    }





    fun some(a: (Node<T>)-> Unit,b:Node<T>){

        a(b)




    }
}

fun dosome ( a: (Int)-> Unit){


        a(100000000)



}





class BinaryTree{

}
