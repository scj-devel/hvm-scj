package test.icecaptools.minitests;

public class Test15 {

    /**
     * @param args
     */
    public static void main(String[] args) {

        StringBuffer buffer = new StringBuffer();
        buffer.append("hej");
        String result = buffer.toString();
        
        Integer i = new Integer(42);
        buffer.append(i.toString());
        Object o1 = i;
        Object o2 = buffer;
        if (o1.equals(o2))
        {
            ;
        }
        
        Integer.parseInt(result.toString());
        
        // new File("Main.java");
        
        /*
         * Inferred creation of the following instance types:
  java/io/BufferedInputStream
  java/io/ByteArrayInputStream
  java/io/DataInputStream
  java/io/EOFException
  java/io/File
  java/io/File$1
  java/io/IOException
  java/lang/ArrayIndexOutOfBoundsException
  java/lang/AssertionError
  java/lang/CharacterData00
  java/lang/CharacterDataLatin1
  java/lang/ClassCastException
  java/lang/CloneNotSupportedException
  java/lang/IllegalAccessException
  java/lang/IllegalArgumentException
  java/lang/IndexOutOfBoundsException
  java/lang/Integer
  java/lang/InternalError
  java/lang/NullPointerException
  java/lang/Object
  java/lang/RuntimeException
  java/lang/RuntimePermission
  java/lang/SecurityException
  java/lang/String
  java/lang/String$CaseInsensitiveComparator
  java/lang/StringBuffer
  java/lang/StringBuilder
  java/lang/StringIndexOutOfBoundsException
  java/lang/ThreadLocal$ThreadLocalMap
  java/lang/ThreadLocal$ThreadLocalMap$Entry
  java/lang/ref/ReferenceQueue
  java/lang/ref/ReferenceQueue$Lock
  java/lang/ref/ReferenceQueue$Null
  java/lang/reflect/ReflectAccess
  java/text/Normalizer$Form
  java/text/ParsePosition
  java/util/HashMap
  java/util/HashMap$Entry
  java/util/HashMap$EntryIterator
  java/util/HashMap$EntrySet
  java/util/MissingResourceException
  java/util/TreeMap
  java/util/TreeMap$Entry
  java/util/TreeMap$EntryIterator
  java/util/TreeMap$EntrySet
  java/util/TreeSet
  java/util/concurrent/atomic/AtomicInteger
  java/util/concurrent/atomic/AtomicReferenceFieldUpdater$AtomicReferenceFieldUpdaterImpl
  java/util/regex/Pattern
  java/util/regex/Pattern$5
  java/util/regex/Pattern$BnM
  java/util/regex/Pattern$BnMS
  java/util/regex/Pattern$Branch
  java/util/regex/Pattern$BranchConn
  java/util/regex/Pattern$GroupCurly
  java/util/regex/Pattern$GroupHead
  java/util/regex/Pattern$GroupTail
  java/util/regex/Pattern$LastNode
  java/util/regex/Pattern$LazyLoop
  java/util/regex/Pattern$Loop
  java/util/regex/Pattern$Node
  java/util/regex/Pattern$Prolog
  java/util/regex/Pattern$Ques
  java/util/regex/Pattern$Slice
  java/util/regex/Pattern$SliceI
  java/util/regex/Pattern$SliceIS
  java/util/regex/Pattern$SliceS
  java/util/regex/Pattern$SliceU
  java/util/regex/Pattern$SliceUS
  java/util/regex/Pattern$Start
  java/util/regex/Pattern$StartS
  java/util/regex/Pattern$TreeInfo
  java/util/regex/PatternSyntaxException
  sun/misc/FDBigInt
  sun/misc/FloatingDecimal
  sun/misc/FloatingDecimal$1
  sun/misc/SoftCache
  sun/misc/SoftCache$EntrySet
  sun/misc/SoftCache$EntrySet$1
  sun/misc/Unsafe
  sun/reflect/ReflectionFactory$GetReflectionFactoryAction
  sun/security/action/GetPropertyAction
  sun/text/normalizer/CharTrie
  sun/text/normalizer/CharTrie$FriendAgent
  sun/text/normalizer/ICUData$1
  sun/text/normalizer/IntTrie
  sun/text/normalizer/NormalizerBase$Mode
  sun/text/normalizer/NormalizerBase$NFCMode
  sun/text/normalizer/NormalizerBase$NFDMode
  sun/text/normalizer/NormalizerBase$NFKCMode
  sun/text/normalizer/NormalizerBase$NFKDMode
  sun/text/normalizer/NormalizerBase$QuickCheckResult
  sun/text/normalizer/NormalizerDataReader
  sun/text/normalizer/NormalizerImpl
  sun/text/normalizer/NormalizerImpl$AuxTrieImpl
  sun/text/normalizer/NormalizerImpl$ComposePartArgs
  sun/text/normalizer/NormalizerImpl$DecomposeArgs
  sun/text/normalizer/NormalizerImpl$FCDTrieImpl
  sun/text/normalizer/NormalizerImpl$NextCCArgs
  sun/text/normalizer/NormalizerImpl$NextCombiningArgs
  sun/text/normalizer/NormalizerImpl$NormTrieImpl
  sun/text/normalizer/NormalizerImpl$PrevArgs
  sun/text/normalizer/NormalizerImpl$RecomposeArgs
  sun/text/normalizer/ReplaceableString
  sun/text/normalizer/ReplaceableUCharacterIterator
  sun/text/normalizer/RuleCharacterIterator
  sun/text/normalizer/UCharacterProperty
  sun/text/normalizer/UCharacterPropertyReader
  sun/text/normalizer/UnicodeSet
  sun/text/normalizer/UnicodeSetIterator
  sun/text/normalizer/VersionInfo */
    }

}
