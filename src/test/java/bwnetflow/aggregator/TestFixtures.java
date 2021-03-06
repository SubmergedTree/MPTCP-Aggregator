package bwnetflow.aggregator;

import bwnetflow.messages.FlowMessageEnrichedPb;
import bwnetflow.messages.MPTCPFlowMessageEnrichedPb;
import bwnetflow.messages.MPTCPMessageProto;
import bwnetflow.serdes.proto.KafkaProtobufDeserializer;
import bwnetflow.serdes.proto.KafkaProtobufSerde;
import bwnetflow.serdes.proto.KafkaProtobufSerializer;
import com.google.protobuf.ByteString;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class TestFixtures {

    public static final String MPTCP_TOPIC = "mptcp-packets";
    public static final String FLOWS_ENRICHED_TOPIC = "flows-enriched";

    public final static StringSerializer stringSerializer = new StringSerializer();
    public final static StringDeserializer stringDeserializer = new StringDeserializer();

    public final static KafkaProtobufSerializer<FlowMessageEnrichedPb.FlowMessage> flowMessageSerializer
            = new KafkaProtobufSerializer<>();

    public final static KafkaProtobufSerializer<MPTCPMessageProto.MPTCPMessage> mptcpMessageSerializer
            = new KafkaProtobufSerializer<>();

    public final static KafkaProtobufSerializer<MPTCPFlowMessageEnrichedPb.MPTCPFlowMessage> mptcpFlowSerializer
            = new KafkaProtobufSerializer<>();


    public final static Serde<MPTCPFlowMessageEnrichedPb.MPTCPFlowMessage> mptcpFlowsSerde =
            new KafkaProtobufSerde<>(MPTCPFlowMessageEnrichedPb.MPTCPFlowMessage.parser());

    public final static KafkaProtobufDeserializer<MPTCPFlowMessageEnrichedPb.MPTCPFlowMessage> mptcpFlowMessageDeserializer
            = new KafkaProtobufDeserializer<>(MPTCPFlowMessageEnrichedPb.MPTCPFlowMessage.parser());

    public static FlowMessageEnrichedPb.FlowMessage enrichedFlowMessage(String srcIp, String dstIp, int srcPort,
                                                                        int dstPort, int seqNum) {
        return FlowMessageEnrichedPb.FlowMessage.newBuilder()
                .setSrcAddr(stringIpToByteString(srcIp))
                .setDstAddr(stringIpToByteString(dstIp))
                .setSrcPort(srcPort)
                .setDstPort(dstPort)
                .setSequenceNum(seqNum)
                .build();
    }

    public static MPTCPFlowMessageEnrichedPb.MPTCPFlowMessage mptcpFlowMessage(String srcIp, String dstIp, int srcPort,
                                                                               int dstPort, int seqNum) {
        return MPTCPFlowMessageEnrichedPb.MPTCPFlowMessage.newBuilder()
                .setSrcAddr(stringIpToByteString(srcIp))
                .setDstAddr(stringIpToByteString(dstIp))
                .setSrcPort(srcPort)
                .setDstPort(dstPort)
                .setSequenceNum(seqNum)
                .build();

    }

    public static MPTCPMessageProto.MPTCPMessage mptcpMessage(String srcIp, String dstIp, int srcPort,
                                                              int dstPort, int seqNum) {
        return MPTCPMessageProto.MPTCPMessage.newBuilder()
                .setSrcAddr(srcIp)
                .setDstAddr(dstIp)
                .setSrcPort(srcPort)
                .setDstPort(dstPort)
                .setSeqNum(seqNum)
                .build();
    }

    public static final FlowMessageEnrichedPb.FlowMessage FLOW_MSG = TestFixtures.enrichedFlowMessage("111.111.111",
            "222.222.222", 1111,2222, 6000);

    public static final MPTCPMessageProto.MPTCPMessage MPTCP_MESSAGE = TestFixtures.mptcpMessage("111.111.111",
            "222.222.222", 1111,2222, 6000);


    public static final FlowMessageEnrichedPb.FlowMessage FLOW_MSG2 = TestFixtures.enrichedFlowMessage("111.111.111",
            "222.222.111", 1111,2222, 6000);

    public static final MPTCPMessageProto.MPTCPMessage MPTCP_MESSAGE2 = TestFixtures.mptcpMessage("111.111.111",
            "222.222.111", 1111,2222, 6000);

    public static final FlowMessageEnrichedPb.FlowMessage FLOW_MSG3 = TestFixtures.enrichedFlowMessage("111.111.111",
            "222.222.100", 1111,2222, 6000);

    private static ByteString stringIpToByteString(String ipString) {
        InetAddress ip = null;
        try {
            ip = InetAddress.getByName(ipString);
        } catch (UnknownHostException e) {
            System.err.println(e.toString());
            return ByteString.EMPTY;
        }
        byte[] bytes = ip.getAddress();
        return ByteString.copyFrom(bytes);
    }
}
