import ByteConversions._

object SerializerProbe extends App {

  case class Dog(name: String, age: Int) extends ByteSerializer
  case class Cat(name: String, age: Int) extends ByteSerializer

  val ted = Dog("Ted", 5)
  val mai = Cat("Mai", 2)

  // We want to serialize Dog class into Array[Byte]
  val bytes = ted.toBytes
  println("Bytes: " + bytes.packedString)

  // We want to unserialize bytes and expect a Dog
  val newTed = bytes.toObject[Dog] //-> Some(Dog)
  println(newTed)

  // We want to unserialize bytes and expect a Cat
  val newTed2 = bytes.toObject[Cat] //-> None
  println(newTed2)

  val bytes2 = mai.toBytes

  val newMai = bytes2.toObject[Cat]
  println(newMai)

}
