import java.io.{ ObjectInputStream, ByteArrayInputStream, ObjectOutputStream, ByteArrayOutputStream }

import scala.reflect.ClassTag

package object ByteConversions {

  trait ByteSerializer extends Serializable {

    def toBytes: Array[Byte] = {
      val bos = new ByteArrayOutputStream
      val oos = new ObjectOutputStream(bos)
      oos.writeObject(this)
      oos.close()

      bos.toByteArray
    }

  }

  implicit class ByteDeserializer[T](bytes: Array[Byte]) {

    lazy val packedString: String =
      bytes.foldLeft("")((acc, x) => acc + ("%02X" format x))

    def toObject[T: ClassTag]: Option[T] = {
      val ois = new ObjectInputStream(new ByteArrayInputStream(bytes))
      val obj = ois.readObject
      ois.close()

      obj match {
        case _: T => Some(obj.asInstanceOf[T])
        case _    => None
      }
    }

  }

}
