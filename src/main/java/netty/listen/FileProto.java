// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: src/main/resources/proto/DataStruct.proto

package netty.listen;

public final class FileProto {
  private FileProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_netty_FileStruct_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_netty_FileStruct_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n)src/main/resources/proto/DataStruct.pr" +
      "oto\022\005netty\"h\n\nFileStruct\022\035\n\004type\030\001 \001(\0162\017" +
      ".netty.FileType\022\037\n\005event\030\002 \001(\0162\020.netty.F" +
      "ileEvent\022\014\n\004path\030\003 \001(\t\022\014\n\004data\030\004 \001(\014*;\n\010" +
      "FileType\022\005\n\001F\020\000\022\005\n\001D\020\001\022\005\n\001B\020\002\022\005\n\001C\020\003\022\005\n\001" +
      "S\020\004\022\005\n\001P\020\005\022\005\n\001L\020\006*/\n\tFileEvent\022\n\n\006CREATE" +
      "\020\000\022\n\n\006DELETE\020\001\022\n\n\006MODIFY\020\002B\033\n\014netty.list" +
      "enB\tFileProtoP\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_netty_FileStruct_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_netty_FileStruct_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_netty_FileStruct_descriptor,
        new java.lang.String[] { "Type", "Event", "Path", "Data", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}