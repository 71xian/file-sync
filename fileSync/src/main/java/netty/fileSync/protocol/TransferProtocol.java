// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: src/main/resources/proto/TransferProtocol.proto

package netty.fileSync.protocol;

public final class TransferProtocol {
  private TransferProtocol() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  /**
   * Protobuf enum {@code netty.sync.protocol.FileType}
   */
  public enum FileType
      implements com.google.protobuf.ProtocolMessageEnum {
    /**
     * <code>FILE = 0;</code>
     */
    FILE(0),
    /**
     * <code>DIR = 1;</code>
     */
    DIR(1),
    UNRECOGNIZED(-1),
    ;

    /**
     * <code>FILE = 0;</code>
     */
    public static final int FILE_VALUE = 0;
    /**
     * <code>DIR = 1;</code>
     */
    public static final int DIR_VALUE = 1;


    public final int getNumber() {
      if (this == UNRECOGNIZED) {
        throw new java.lang.IllegalArgumentException(
            "Can't get the number of an unknown enum value.");
      }
      return value;
    }

    /**
     * @param value The numeric wire value of the corresponding enum entry.
     * @return The enum associated with the given numeric wire value.
     * @deprecated Use {@link #forNumber(int)} instead.
     */
    @java.lang.Deprecated
    public static FileType valueOf(int value) {
      return forNumber(value);
    }

    /**
     * @param value The numeric wire value of the corresponding enum entry.
     * @return The enum associated with the given numeric wire value.
     */
    public static FileType forNumber(int value) {
      switch (value) {
        case 0: return FILE;
        case 1: return DIR;
        default: return null;
      }
    }

    public static com.google.protobuf.Internal.EnumLiteMap<FileType>
        internalGetValueMap() {
      return internalValueMap;
    }
    private static final com.google.protobuf.Internal.EnumLiteMap<
        FileType> internalValueMap =
          new com.google.protobuf.Internal.EnumLiteMap<FileType>() {
            public FileType findValueByNumber(int number) {
              return FileType.forNumber(number);
            }
          };

    public final com.google.protobuf.Descriptors.EnumValueDescriptor
        getValueDescriptor() {
      if (this == UNRECOGNIZED) {
        throw new java.lang.IllegalStateException(
            "Can't get the descriptor of an unrecognized enum value.");
      }
      return getDescriptor().getValues().get(ordinal());
    }
    public final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptorForType() {
      return getDescriptor();
    }
    public static final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptor() {
      return netty.fileSync.protocol.TransferProtocol.getDescriptor().getEnumTypes().get(0);
    }

    private static final FileType[] VALUES = values();

    public static FileType valueOf(
        com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
      if (desc.getType() != getDescriptor()) {
        throw new java.lang.IllegalArgumentException(
          "EnumValueDescriptor is not for this type.");
      }
      if (desc.getIndex() == -1) {
        return UNRECOGNIZED;
      }
      return VALUES[desc.getIndex()];
    }

    private final int value;

    private FileType(int value) {
      this.value = value;
    }

    // @@protoc_insertion_point(enum_scope:netty.sync.protocol.FileType)
  }

  /**
   * Protobuf enum {@code netty.sync.protocol.FileEvent}
   */
  public enum FileEvent
      implements com.google.protobuf.ProtocolMessageEnum {
    /**
     * <code>CREATE = 0;</code>
     */
    CREATE(0),
    /**
     * <code>DELETE = 1;</code>
     */
    DELETE(1),
    /**
     * <code>MODIFY = 2;</code>
     */
    MODIFY(2),
    UNRECOGNIZED(-1),
    ;

    /**
     * <code>CREATE = 0;</code>
     */
    public static final int CREATE_VALUE = 0;
    /**
     * <code>DELETE = 1;</code>
     */
    public static final int DELETE_VALUE = 1;
    /**
     * <code>MODIFY = 2;</code>
     */
    public static final int MODIFY_VALUE = 2;


    public final int getNumber() {
      if (this == UNRECOGNIZED) {
        throw new java.lang.IllegalArgumentException(
            "Can't get the number of an unknown enum value.");
      }
      return value;
    }

    /**
     * @param value The numeric wire value of the corresponding enum entry.
     * @return The enum associated with the given numeric wire value.
     * @deprecated Use {@link #forNumber(int)} instead.
     */
    @java.lang.Deprecated
    public static FileEvent valueOf(int value) {
      return forNumber(value);
    }

    /**
     * @param value The numeric wire value of the corresponding enum entry.
     * @return The enum associated with the given numeric wire value.
     */
    public static FileEvent forNumber(int value) {
      switch (value) {
        case 0: return CREATE;
        case 1: return DELETE;
        case 2: return MODIFY;
        default: return null;
      }
    }

    public static com.google.protobuf.Internal.EnumLiteMap<FileEvent>
        internalGetValueMap() {
      return internalValueMap;
    }
    private static final com.google.protobuf.Internal.EnumLiteMap<
        FileEvent> internalValueMap =
          new com.google.protobuf.Internal.EnumLiteMap<FileEvent>() {
            public FileEvent findValueByNumber(int number) {
              return FileEvent.forNumber(number);
            }
          };

    public final com.google.protobuf.Descriptors.EnumValueDescriptor
        getValueDescriptor() {
      if (this == UNRECOGNIZED) {
        throw new java.lang.IllegalStateException(
            "Can't get the descriptor of an unrecognized enum value.");
      }
      return getDescriptor().getValues().get(ordinal());
    }
    public final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptorForType() {
      return getDescriptor();
    }
    public static final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptor() {
      return netty.fileSync.protocol.TransferProtocol.getDescriptor().getEnumTypes().get(1);
    }

    private static final FileEvent[] VALUES = values();

    public static FileEvent valueOf(
        com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
      if (desc.getType() != getDescriptor()) {
        throw new java.lang.IllegalArgumentException(
          "EnumValueDescriptor is not for this type.");
      }
      if (desc.getIndex() == -1) {
        return UNRECOGNIZED;
      }
      return VALUES[desc.getIndex()];
    }

    private final int value;

    private FileEvent(int value) {
      this.value = value;
    }

    // @@protoc_insertion_point(enum_scope:netty.sync.protocol.FileEvent)
  }

  public interface FileRequestOrBuilder extends
      // @@protoc_insertion_point(interface_extends:netty.sync.protocol.FileRequest)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>.netty.sync.protocol.FileType type = 1;</code>
     * @return The enum numeric value on the wire for type.
     */
    int getTypeValue();
    /**
     * <code>.netty.sync.protocol.FileType type = 1;</code>
     * @return The type.
     */
    netty.fileSync.protocol.TransferProtocol.FileType getType();

    /**
     * <code>.netty.sync.protocol.FileEvent event = 2;</code>
     * @return The enum numeric value on the wire for event.
     */
    int getEventValue();
    /**
     * <code>.netty.sync.protocol.FileEvent event = 2;</code>
     * @return The event.
     */
    netty.fileSync.protocol.TransferProtocol.FileEvent getEvent();

    /**
     * <code>string path = 3;</code>
     * @return The path.
     */
    java.lang.String getPath();
    /**
     * <code>string path = 3;</code>
     * @return The bytes for path.
     */
    com.google.protobuf.ByteString
        getPathBytes();

    /**
     * <code>optional bytes data = 4;</code>
     * @return Whether the data field is set.
     */
    boolean hasData();
    /**
     * <code>optional bytes data = 4;</code>
     * @return The data.
     */
    com.google.protobuf.ByteString getData();
  }
  /**
   * Protobuf type {@code netty.sync.protocol.FileRequest}
   */
  public static final class FileRequest extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:netty.sync.protocol.FileRequest)
      FileRequestOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use FileRequest.newBuilder() to construct.
    private FileRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private FileRequest() {
      type_ = 0;
      event_ = 0;
      path_ = "";
      data_ = com.google.protobuf.ByteString.EMPTY;
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new FileRequest();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return netty.fileSync.protocol.TransferProtocol.internal_static_netty_sync_protocol_FileRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return netty.fileSync.protocol.TransferProtocol.internal_static_netty_sync_protocol_FileRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              netty.fileSync.protocol.TransferProtocol.FileRequest.class, netty.fileSync.protocol.TransferProtocol.FileRequest.Builder.class);
    }

    private int bitField0_;
    public static final int TYPE_FIELD_NUMBER = 1;
    private int type_ = 0;
    /**
     * <code>.netty.sync.protocol.FileType type = 1;</code>
     * @return The enum numeric value on the wire for type.
     */
    @java.lang.Override public int getTypeValue() {
      return type_;
    }
    /**
     * <code>.netty.sync.protocol.FileType type = 1;</code>
     * @return The type.
     */
    @java.lang.Override public netty.fileSync.protocol.TransferProtocol.FileType getType() {
      netty.fileSync.protocol.TransferProtocol.FileType result = netty.fileSync.protocol.TransferProtocol.FileType.forNumber(type_);
      return result == null ? netty.fileSync.protocol.TransferProtocol.FileType.UNRECOGNIZED : result;
    }

    public static final int EVENT_FIELD_NUMBER = 2;
    private int event_ = 0;
    /**
     * <code>.netty.sync.protocol.FileEvent event = 2;</code>
     * @return The enum numeric value on the wire for event.
     */
    @java.lang.Override public int getEventValue() {
      return event_;
    }
    /**
     * <code>.netty.sync.protocol.FileEvent event = 2;</code>
     * @return The event.
     */
    @java.lang.Override public netty.fileSync.protocol.TransferProtocol.FileEvent getEvent() {
      netty.fileSync.protocol.TransferProtocol.FileEvent result = netty.fileSync.protocol.TransferProtocol.FileEvent.forNumber(event_);
      return result == null ? netty.fileSync.protocol.TransferProtocol.FileEvent.UNRECOGNIZED : result;
    }

    public static final int PATH_FIELD_NUMBER = 3;
    @SuppressWarnings("serial")
    private volatile java.lang.Object path_ = "";
    /**
     * <code>string path = 3;</code>
     * @return The path.
     */
    @java.lang.Override
    public java.lang.String getPath() {
      java.lang.Object ref = path_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        path_ = s;
        return s;
      }
    }
    /**
     * <code>string path = 3;</code>
     * @return The bytes for path.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
        getPathBytes() {
      java.lang.Object ref = path_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        path_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int DATA_FIELD_NUMBER = 4;
    private com.google.protobuf.ByteString data_ = com.google.protobuf.ByteString.EMPTY;
    /**
     * <code>optional bytes data = 4;</code>
     * @return Whether the data field is set.
     */
    @java.lang.Override
    public boolean hasData() {
      return ((bitField0_ & 0x00000001) != 0);
    }
    /**
     * <code>optional bytes data = 4;</code>
     * @return The data.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString getData() {
      return data_;
    }

    private byte memoizedIsInitialized = -1;
    @java.lang.Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (type_ != netty.fileSync.protocol.TransferProtocol.FileType.FILE.getNumber()) {
        output.writeEnum(1, type_);
      }
      if (event_ != netty.fileSync.protocol.TransferProtocol.FileEvent.CREATE.getNumber()) {
        output.writeEnum(2, event_);
      }
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(path_)) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 3, path_);
      }
      if (((bitField0_ & 0x00000001) != 0)) {
        output.writeBytes(4, data_);
      }
      getUnknownFields().writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (type_ != netty.fileSync.protocol.TransferProtocol.FileType.FILE.getNumber()) {
        size += com.google.protobuf.CodedOutputStream
          .computeEnumSize(1, type_);
      }
      if (event_ != netty.fileSync.protocol.TransferProtocol.FileEvent.CREATE.getNumber()) {
        size += com.google.protobuf.CodedOutputStream
          .computeEnumSize(2, event_);
      }
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(path_)) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, path_);
      }
      if (((bitField0_ & 0x00000001) != 0)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(4, data_);
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof netty.fileSync.protocol.TransferProtocol.FileRequest)) {
        return super.equals(obj);
      }
      netty.fileSync.protocol.TransferProtocol.FileRequest other = (netty.fileSync.protocol.TransferProtocol.FileRequest) obj;

      if (type_ != other.type_) return false;
      if (event_ != other.event_) return false;
      if (!getPath()
          .equals(other.getPath())) return false;
      if (hasData() != other.hasData()) return false;
      if (hasData()) {
        if (!getData()
            .equals(other.getData())) return false;
      }
      if (!getUnknownFields().equals(other.getUnknownFields())) return false;
      return true;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + TYPE_FIELD_NUMBER;
      hash = (53 * hash) + type_;
      hash = (37 * hash) + EVENT_FIELD_NUMBER;
      hash = (53 * hash) + event_;
      hash = (37 * hash) + PATH_FIELD_NUMBER;
      hash = (53 * hash) + getPath().hashCode();
      if (hasData()) {
        hash = (37 * hash) + DATA_FIELD_NUMBER;
        hash = (53 * hash) + getData().hashCode();
      }
      hash = (29 * hash) + getUnknownFields().hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static netty.fileSync.protocol.TransferProtocol.FileRequest parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static netty.fileSync.protocol.TransferProtocol.FileRequest parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static netty.fileSync.protocol.TransferProtocol.FileRequest parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static netty.fileSync.protocol.TransferProtocol.FileRequest parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static netty.fileSync.protocol.TransferProtocol.FileRequest parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static netty.fileSync.protocol.TransferProtocol.FileRequest parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static netty.fileSync.protocol.TransferProtocol.FileRequest parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static netty.fileSync.protocol.TransferProtocol.FileRequest parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static netty.fileSync.protocol.TransferProtocol.FileRequest parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static netty.fileSync.protocol.TransferProtocol.FileRequest parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static netty.fileSync.protocol.TransferProtocol.FileRequest parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static netty.fileSync.protocol.TransferProtocol.FileRequest parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(netty.fileSync.protocol.TransferProtocol.FileRequest prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @java.lang.Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code netty.sync.protocol.FileRequest}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:netty.sync.protocol.FileRequest)
        netty.fileSync.protocol.TransferProtocol.FileRequestOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return netty.fileSync.protocol.TransferProtocol.internal_static_netty_sync_protocol_FileRequest_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return netty.fileSync.protocol.TransferProtocol.internal_static_netty_sync_protocol_FileRequest_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                netty.fileSync.protocol.TransferProtocol.FileRequest.class, netty.fileSync.protocol.TransferProtocol.FileRequest.Builder.class);
      }

      // Construct using netty.sync.protocol.TransferProtocol.FileRequest.newBuilder()
      private Builder() {

      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);

      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
        bitField0_ = 0;
        type_ = 0;
        event_ = 0;
        path_ = "";
        data_ = com.google.protobuf.ByteString.EMPTY;
        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return netty.fileSync.protocol.TransferProtocol.internal_static_netty_sync_protocol_FileRequest_descriptor;
      }

      @java.lang.Override
      public netty.fileSync.protocol.TransferProtocol.FileRequest getDefaultInstanceForType() {
        return netty.fileSync.protocol.TransferProtocol.FileRequest.getDefaultInstance();
      }

      @java.lang.Override
      public netty.fileSync.protocol.TransferProtocol.FileRequest build() {
        netty.fileSync.protocol.TransferProtocol.FileRequest result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public netty.fileSync.protocol.TransferProtocol.FileRequest buildPartial() {
        netty.fileSync.protocol.TransferProtocol.FileRequest result = new netty.fileSync.protocol.TransferProtocol.FileRequest(this);
        if (bitField0_ != 0) { buildPartial0(result); }
        onBuilt();
        return result;
      }

      private void buildPartial0(netty.fileSync.protocol.TransferProtocol.FileRequest result) {
        int from_bitField0_ = bitField0_;
        if (((from_bitField0_ & 0x00000001) != 0)) {
          result.type_ = type_;
        }
        if (((from_bitField0_ & 0x00000002) != 0)) {
          result.event_ = event_;
        }
        if (((from_bitField0_ & 0x00000004) != 0)) {
          result.path_ = path_;
        }
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000008) != 0)) {
          result.data_ = data_;
          to_bitField0_ |= 0x00000001;
        }
        result.bitField0_ |= to_bitField0_;
      }

      @java.lang.Override
      public Builder clone() {
        return super.clone();
      }
      @java.lang.Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.setField(field, value);
      }
      @java.lang.Override
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return super.clearField(field);
      }
      @java.lang.Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return super.clearOneof(oneof);
      }
      @java.lang.Override
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, java.lang.Object value) {
        return super.setRepeatedField(field, index, value);
      }
      @java.lang.Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.addRepeatedField(field, value);
      }
      @java.lang.Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof netty.fileSync.protocol.TransferProtocol.FileRequest) {
          return mergeFrom((netty.fileSync.protocol.TransferProtocol.FileRequest)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(netty.fileSync.protocol.TransferProtocol.FileRequest other) {
        if (other == netty.fileSync.protocol.TransferProtocol.FileRequest.getDefaultInstance()) return this;
        if (other.type_ != 0) {
          setTypeValue(other.getTypeValue());
        }
        if (other.event_ != 0) {
          setEventValue(other.getEventValue());
        }
        if (!other.getPath().isEmpty()) {
          path_ = other.path_;
          bitField0_ |= 0x00000004;
          onChanged();
        }
        if (other.hasData()) {
          setData(other.getData());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        onChanged();
        return this;
      }

      @java.lang.Override
      public final boolean isInitialized() {
        return true;
      }

      @java.lang.Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        if (extensionRegistry == null) {
          throw new java.lang.NullPointerException();
        }
        try {
          boolean done = false;
          while (!done) {
            int tag = input.readTag();
            switch (tag) {
              case 0:
                done = true;
                break;
              case 8: {
                type_ = input.readEnum();
                bitField0_ |= 0x00000001;
                break;
              } // case 8
              case 16: {
                event_ = input.readEnum();
                bitField0_ |= 0x00000002;
                break;
              } // case 16
              case 26: {
                path_ = input.readStringRequireUtf8();
                bitField0_ |= 0x00000004;
                break;
              } // case 26
              case 34: {
                data_ = input.readBytes();
                bitField0_ |= 0x00000008;
                break;
              } // case 34
              default: {
                if (!super.parseUnknownField(input, extensionRegistry, tag)) {
                  done = true; // was an endgroup tag
                }
                break;
              } // default:
            } // switch (tag)
          } // while (!done)
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          throw e.unwrapIOException();
        } finally {
          onChanged();
        } // finally
        return this;
      }
      private int bitField0_;

      private int type_ = 0;
      /**
       * <code>.netty.sync.protocol.FileType type = 1;</code>
       * @return The enum numeric value on the wire for type.
       */
      @java.lang.Override public int getTypeValue() {
        return type_;
      }
      /**
       * <code>.netty.sync.protocol.FileType type = 1;</code>
       * @param value The enum numeric value on the wire for type to set.
       * @return This builder for chaining.
       */
      public Builder setTypeValue(int value) {
        type_ = value;
        bitField0_ |= 0x00000001;
        onChanged();
        return this;
      }
      /**
       * <code>.netty.sync.protocol.FileType type = 1;</code>
       * @return The type.
       */
      @java.lang.Override
      public netty.fileSync.protocol.TransferProtocol.FileType getType() {
        netty.fileSync.protocol.TransferProtocol.FileType result = netty.fileSync.protocol.TransferProtocol.FileType.forNumber(type_);
        return result == null ? netty.fileSync.protocol.TransferProtocol.FileType.UNRECOGNIZED : result;
      }
      /**
       * <code>.netty.sync.protocol.FileType type = 1;</code>
       * @param value The type to set.
       * @return This builder for chaining.
       */
      public Builder setType(netty.fileSync.protocol.TransferProtocol.FileType value) {
        if (value == null) {
          throw new NullPointerException();
        }
        bitField0_ |= 0x00000001;
        type_ = value.getNumber();
        onChanged();
        return this;
      }
      /**
       * <code>.netty.sync.protocol.FileType type = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearType() {
        bitField0_ = (bitField0_ & ~0x00000001);
        type_ = 0;
        onChanged();
        return this;
      }

      private int event_ = 0;
      /**
       * <code>.netty.sync.protocol.FileEvent event = 2;</code>
       * @return The enum numeric value on the wire for event.
       */
      @java.lang.Override public int getEventValue() {
        return event_;
      }
      /**
       * <code>.netty.sync.protocol.FileEvent event = 2;</code>
       * @param value The enum numeric value on the wire for event to set.
       * @return This builder for chaining.
       */
      public Builder setEventValue(int value) {
        event_ = value;
        bitField0_ |= 0x00000002;
        onChanged();
        return this;
      }
      /**
       * <code>.netty.sync.protocol.FileEvent event = 2;</code>
       * @return The event.
       */
      @java.lang.Override
      public netty.fileSync.protocol.TransferProtocol.FileEvent getEvent() {
        netty.fileSync.protocol.TransferProtocol.FileEvent result = netty.fileSync.protocol.TransferProtocol.FileEvent.forNumber(event_);
        return result == null ? netty.fileSync.protocol.TransferProtocol.FileEvent.UNRECOGNIZED : result;
      }
      /**
       * <code>.netty.sync.protocol.FileEvent event = 2;</code>
       * @param value The event to set.
       * @return This builder for chaining.
       */
      public Builder setEvent(netty.fileSync.protocol.TransferProtocol.FileEvent value) {
        if (value == null) {
          throw new NullPointerException();
        }
        bitField0_ |= 0x00000002;
        event_ = value.getNumber();
        onChanged();
        return this;
      }
      /**
       * <code>.netty.sync.protocol.FileEvent event = 2;</code>
       * @return This builder for chaining.
       */
      public Builder clearEvent() {
        bitField0_ = (bitField0_ & ~0x00000002);
        event_ = 0;
        onChanged();
        return this;
      }

      private java.lang.Object path_ = "";
      /**
       * <code>string path = 3;</code>
       * @return The path.
       */
      public java.lang.String getPath() {
        java.lang.Object ref = path_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          path_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string path = 3;</code>
       * @return The bytes for path.
       */
      public com.google.protobuf.ByteString
          getPathBytes() {
        java.lang.Object ref = path_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          path_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string path = 3;</code>
       * @param value The path to set.
       * @return This builder for chaining.
       */
      public Builder setPath(
          java.lang.String value) {
        if (value == null) { throw new NullPointerException(); }
        path_ = value;
        bitField0_ |= 0x00000004;
        onChanged();
        return this;
      }
      /**
       * <code>string path = 3;</code>
       * @return This builder for chaining.
       */
      public Builder clearPath() {
        path_ = getDefaultInstance().getPath();
        bitField0_ = (bitField0_ & ~0x00000004);
        onChanged();
        return this;
      }
      /**
       * <code>string path = 3;</code>
       * @param value The bytes for path to set.
       * @return This builder for chaining.
       */
      public Builder setPathBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) { throw new NullPointerException(); }
        checkByteStringIsUtf8(value);
        path_ = value;
        bitField0_ |= 0x00000004;
        onChanged();
        return this;
      }

      private com.google.protobuf.ByteString data_ = com.google.protobuf.ByteString.EMPTY;
      /**
       * <code>optional bytes data = 4;</code>
       * @return Whether the data field is set.
       */
      @java.lang.Override
      public boolean hasData() {
        return ((bitField0_ & 0x00000008) != 0);
      }
      /**
       * <code>optional bytes data = 4;</code>
       * @return The data.
       */
      @java.lang.Override
      public com.google.protobuf.ByteString getData() {
        return data_;
      }
      /**
       * <code>optional bytes data = 4;</code>
       * @param value The data to set.
       * @return This builder for chaining.
       */
      public Builder setData(com.google.protobuf.ByteString value) {
        if (value == null) { throw new NullPointerException(); }
        data_ = value;
        bitField0_ |= 0x00000008;
        onChanged();
        return this;
      }
      /**
       * <code>optional bytes data = 4;</code>
       * @return This builder for chaining.
       */
      public Builder clearData() {
        bitField0_ = (bitField0_ & ~0x00000008);
        data_ = getDefaultInstance().getData();
        onChanged();
        return this;
      }
      @java.lang.Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @java.lang.Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:netty.sync.protocol.FileRequest)
    }

    // @@protoc_insertion_point(class_scope:netty.sync.protocol.FileRequest)
    private static final netty.fileSync.protocol.TransferProtocol.FileRequest DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new netty.fileSync.protocol.TransferProtocol.FileRequest();
    }

    public static netty.fileSync.protocol.TransferProtocol.FileRequest getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<FileRequest>
        PARSER = new com.google.protobuf.AbstractParser<FileRequest>() {
      @java.lang.Override
      public FileRequest parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        Builder builder = newBuilder();
        try {
          builder.mergeFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          throw e.setUnfinishedMessage(builder.buildPartial());
        } catch (com.google.protobuf.UninitializedMessageException e) {
          throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
        } catch (java.io.IOException e) {
          throw new com.google.protobuf.InvalidProtocolBufferException(e)
              .setUnfinishedMessage(builder.buildPartial());
        }
        return builder.buildPartial();
      }
    };

    public static com.google.protobuf.Parser<FileRequest> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<FileRequest> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public netty.fileSync.protocol.TransferProtocol.FileRequest getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_netty_sync_protocol_FileRequest_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_netty_sync_protocol_FileRequest_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n/src/main/resources/proto/TransferProto" +
      "col.proto\022\023netty.sync.protocol\"\223\001\n\013FileR" +
      "equest\022+\n\004type\030\001 \001(\0162\035.netty.sync.protoc" +
      "ol.FileType\022-\n\005event\030\002 \001(\0162\036.netty.sync." +
      "protocol.FileEvent\022\014\n\004path\030\003 \001(\t\022\021\n\004data" +
      "\030\004 \001(\014H\000\210\001\001B\007\n\005_data*\035\n\010FileType\022\010\n\004FILE" +
      "\020\000\022\007\n\003DIR\020\001*/\n\tFileEvent\022\n\n\006CREATE\020\000\022\n\n\006" +
      "DELETE\020\001\022\n\n\006MODIFY\020\002B\026B\020TransferProtocol" +
      "H\001P\000b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_netty_sync_protocol_FileRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_netty_sync_protocol_FileRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_netty_sync_protocol_FileRequest_descriptor,
        new java.lang.String[] { "Type", "Event", "Path", "Data", "Data", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}