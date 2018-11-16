package com.cristik.common.utils;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CIDRUtil {

    /**
     * get the network address
     *
     * @param address
     * @return
     */
    public static String getNetworkAddress(String address) {
        return buildCIDR(address).getNetworkAddress().getHostAddress();
    }

    /**
     * get the broadcast address
     *
     * @param address
     * @return
     */
    public static String getBroadcastAddress(String address) {
        return buildCIDR(address).getBroadcastAddress().getHostAddress();
    }

    /**
     * get fist address
     *
     * @param address
     * @return
     */
    public static String getFirstAddress(String address) {
        return buildCIDR(address).getFirstAddress();
    }

    /**
     * get last address
     *
     * @param address
     * @return
     */
    public static String getLastAddress(String address) {
        return buildCIDR(address).getLastAddress();
    }


    /**
     * get the sub CIDR block
     *
     * @param address       CIDR block
     * @param subMaskLength sub CIDR block length
     * @param index         the index of subBlock in blocks begin from 1
     * @return
     */
    public static String getSubCIDRBlockByIndex(String address, Integer subMaskLength, Integer index) {
        Network network = buildCIDR(address);
        //check the input is valid
        if (subMaskLength <= network.getMaskLength()) {
            throw new IllegalArgumentException("invalid sub maskLength");
        }
        int totalParts = (int) Math.pow(2, subMaskLength - network.getMaskLength());
        if (index < 1 || index > totalParts) {
            throw new IllegalArgumentException("index is out of sub blocks");
        }

        BigInteger blockSize = subCIDRBlockSize(subMaskLength);
        BigInteger networkAddress = wrapBigInteger(network.getNetworkAddress());
        while (index > 0) {
            networkAddress = networkAddress.add(blockSize);
            index--;
        }
        return wrapInetAddress(networkAddress).getHostAddress() + "/" + subMaskLength;
    }

    /**
     * check if two CIDR block has conflict.
     *
     * @param subAddress1
     * @param subAddress2
     * @return
     */
    public static boolean isConflict(String subAddress1, String subAddress2) {
        Network network1 = buildCIDR(subAddress1);
        Network network2 = buildCIDR(subAddress2);
        return !(wrapBigInteger(network1.getNetworkAddress()).compareTo(wrapBigInteger(network1.getBroadcastAddress())) > 0 ||
                wrapBigInteger(network2.getNetworkAddress()).compareTo(wrapBigInteger(network1.getBroadcastAddress())) > 0);
    }

    /**
     * get available address
     *
     * @param address
     * @param subAddresses
     * @return
     * @throws UnknownHostException
     */
    public static List<String> getAvailableCIDRBlock(String address, List<String> subAddresses) {
        if (!checkInRange(address, subAddresses)) {
            throw new IllegalArgumentException("the sub CIDR is not in address");
        }
        if (checkConflict(subAddresses)) {
            throw new IllegalArgumentException("subAddress is conflict");
        }
        List<BigInteger> usedBlocks = new ArrayList<>();
        List<Range> availableBlocks = new ArrayList<>();
        subAddresses.add(address);
        for (String e : subAddresses) {
            Network network = buildCIDR(e);
            usedBlocks.add(wrapBigInteger(network.getNetworkAddress()));
            usedBlocks.add(wrapBigInteger(network.getBroadcastAddress()));
        }
        Collections.sort(usedBlocks);
        for (int i = 0; i < usedBlocks.size() / 2; i++) {
            BigInteger networkAddress = usedBlocks.get(i * 2);
            BigInteger broadcastAddress = usedBlocks.get(i * 2 + 1);
            if (i == 0) {
                broadcastAddress = broadcastAddress.subtract(BigInteger.ONE);
            } else if (i == usedBlocks.size() / 2 - 1) {
                networkAddress = networkAddress.add(BigInteger.ONE);
            } else {
                networkAddress = networkAddress.add(BigInteger.ONE);
                broadcastAddress = broadcastAddress.subtract(BigInteger.ONE);
            }
            if (!(broadcastAddress.subtract(networkAddress).equals(BigInteger.ONE) ||
                    networkAddress.subtract(broadcastAddress).equals(BigInteger.ONE))) {
                availableBlocks.add(new Range(networkAddress, broadcastAddress));
            }
        }
        List<String> blocks = new ArrayList<>();
        for (Range range : availableBlocks) {
            BigInteger firstAddress = range.getFirstAddress();
            BigInteger lastAddress = range.getLastAddress();
            blocks.addAll(getCIDRBlocks(firstAddress, lastAddress));
        }
        return blocks;
    }

    public static List<String> getCIDRblocks(String address1, String address2) {
        BigInteger firstAddress = wrapBigInteger(address1);
        BigInteger lastAddress = wrapBigInteger(address2);
        if (firstAddress.compareTo(lastAddress) > 0) {
            return getCIDRBlocks(lastAddress, firstAddress);
        }else {
            return getCIDRBlocks(firstAddress, lastAddress);
        }
    }

    /**
     * check if all subAddress is in the address
     *
     * @param address
     * @param subAddresses
     * @return
     */
    private static boolean checkInRange(String address, List<String> subAddresses) {
        for (String e : subAddresses) {
            if (!checkInRange(address, e)) {
                return false;
            }
        }
        return true;
    }

    /**
     * check if the subAddress is in the address
     *
     * @param address
     * @param subAddress
     * @return
     */
    private static boolean checkInRange(String address, String subAddress) {
        BigInteger networkAddress = buildCIDR(address).getNetworkAddressValue();
        BigInteger broadcastAddress = buildCIDR(address).getBroadcastAddressValue();
        BigInteger subNetworkAddress = buildCIDR(subAddress).getNetworkAddressValue();
        BigInteger subBroadcastAddress = buildCIDR(subAddress).getBroadcastAddressValue();
        return subNetworkAddress.compareTo(networkAddress) >= 0 &&
                subBroadcastAddress.compareTo(broadcastAddress) <= 0;
    }

    /**
     * check if the addresses has conflict to each other
     *
     * @param address
     * @return
     */
    private static boolean checkConflict(List<String> address) {
        for (int i = 0; i < address.size() - 1; i++) {
            for (int j = i + 1; j < address.size(); j++) {
                if (isConflict(address.get(i), address.get(j))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * calculate the CIDR blocks with the first and last address
     * @param firstAddress
     * @param lastAddress
     * @return
     */
    private static List<String> getCIDRBlocks(BigInteger firstAddress, BigInteger lastAddress) {
        Integer blockSize = Integer.parseInt(lastAddress.subtract(firstAddress).add(BigInteger.ONE).toString());
        int range = (int) (Math.log(blockSize) / Math.log(2));
        while (!checkFistAddressMatch(firstAddress, range)) {
            range--;
        }

        List<String> blocks = new ArrayList<>();
        blocks.add(wrapInetAddress(firstAddress).getHostAddress() + "/" + (32 - range));
        if (!checkLastAddressMatch(firstAddress, lastAddress, range)) {
            firstAddress = firstAddress.add(new BigInteger(Integer.toString((int) Math.pow(2, range)), 10));
            blocks.addAll(getCIDRBlocks(firstAddress, lastAddress));
        }
        return blocks;
    }

    /**
     * check the firstAddress match the the networkAddress of block
     * @param firstAddress
     * @param range
     * @return
     */
    private static boolean checkFistAddressMatch(BigInteger firstAddress, Integer range) {
        Network network = buildCIDR(wrapInetAddress(firstAddress).getHostAddress() + "/" + (32 - range));
        return network.getNetworkAddressValue().equals(firstAddress);
    }

    /**
     * check the lastAddress match the broadcast of block
     * @param firstAddress
     * @param lastAddress
     * @param range
     * @return
     */
    private static boolean checkLastAddressMatch(BigInteger firstAddress, BigInteger lastAddress, Integer range) {
        Network network = buildCIDR(wrapInetAddress(firstAddress).getHostAddress() + "/" + (32 - range));
        return network.getBroadcastAddressValue().equals(lastAddress);
    }

    /**
     * calculate the block size of the mask
     *
     * @param maskLength
     * @return
     */
    private static BigInteger subCIDRBlockSize(Integer maskLength) {
        BigInteger blockSize = buildMask(maskLength).not().add(new BigInteger("1", 10));
        return blockSize;
    }

    /**
     * parse CIDR block address into an object with details
     *
     * @param addressBlock
     * @return
     */
    private static Network buildCIDR(String addressBlock) {
        if (addressBlock == null || !addressBlock.contains("/")) {
            throw new IllegalArgumentException("invalid input");
        }
        String address = addressBlock.substring(0, addressBlock.indexOf("/"));
        Integer networkRange = Integer.parseInt(addressBlock.substring(addressBlock.indexOf("/") + 1));
        return new Network(getIPV4InetAddressByName(address), networkRange);
    }

    /**
     * parse ipv4 string into InetAddress
     *
     * @param address
     * @return
     */
    private static InetAddress getIPV4InetAddressByName(String address) {
        InetAddress inetAddress;
        try {
            inetAddress = InetAddress.getByName(address);
        } catch (UnknownHostException e) {
            throw new IllegalArgumentException("invalid input");
        }
        if (inetAddress.getAddress().length != 4) {
            throw new IllegalArgumentException("only support ipv4");
        }
        return inetAddress;
    }

    /**
     * get the mask according to the mask length
     *
     * @param maskLength
     * @return
     */
    private static BigInteger buildMask(Integer maskLength) {
        return buildIPv4FullMask().shiftRight(maskLength);//子网掩码
    }

    /**
     * get the full mask of ipv4
     *
     * @return
     */
    private static BigInteger buildIPv4FullMask() {
        ByteBuffer fullMask;
        fullMask = ByteBuffer.allocate(4).putInt(-1);
        return new BigInteger(1, fullMask.array()).not();
    }

    /**
     * get the full mask of ipv6
     *
     * @return
     */
    private static BigInteger buildIPv6FullMask() {
        ByteBuffer fullMask;
        fullMask = ByteBuffer.allocate(16).putLong(-1L).putLong(-1L);
        return new BigInteger(1, fullMask.array()).not();
    }

    /**
     * wrap InetAddress into bigInteger
     *
     * @param inetAddress
     * @return
     */
    private static BigInteger wrapBigInteger(InetAddress inetAddress) {
        ByteBuffer ipByte = ByteBuffer.wrap(inetAddress.getAddress());
        BigInteger ip = new BigInteger(1, ipByte.array());
        return ip;
    }

    /**
     * wrap BigInteger into InetAddress
     *
     * @param bigInteger
     * @return
     */
    private static InetAddress wrapInetAddress(BigInteger bigInteger) {
        InetAddress inetAddress;
        try {
            inetAddress = InetAddress.getByAddress(parseBytes(bigInteger));
        } catch (UnknownHostException e) {
            throw new IllegalArgumentException("the input is not a valid IP");
        }
        return inetAddress;
    }

    /**
     * wrap address into BigInteger
     * @param address
     * @return
     */
    private static BigInteger wrapBigInteger(String address) {
        InetAddress inetAddress;
        try {
            inetAddress = InetAddress.getByName(address);
        } catch (UnknownHostException e) {
            throw new IllegalArgumentException("invalid ip address");
        }
        return wrapBigInteger(inetAddress);
    }

    private static byte[] parseBytes(BigInteger bigInteger) {
        byte[] bytes = bigInteger.toByteArray();
        if(bytes.length == 4) {
            return bytes;
        }
        byte[] ipBytes = new byte[4];
        for(int i=0;i<4;i++) {
            ipBytes[i] = bytes[i+1];
        }
        return ipBytes;
    }

    private static class Range {
        private BigInteger firstAddress;
        private BigInteger lastAddress;

        public Range(BigInteger firstAddress, BigInteger lastAddress) {
            this.firstAddress = firstAddress;
            this.lastAddress = lastAddress;
        }

        public BigInteger getFirstAddress() {
            return firstAddress;
        }

        public BigInteger getLastAddress() {
            return lastAddress;
        }
    }

    private static class Network {
        private InetAddress networkAddress;
        private InetAddress broadcastAddress;
        private String firstAddress;
        private String lastAddress;
        private int maskLength;

        public Network(InetAddress inetAddress, Integer maskLength) {
            this.maskLength = maskLength;
            calculate(inetAddress, maskLength);
        }

        private void calculate(InetAddress inetAddress, Integer prefixLength) {
            if (prefixLength < 0 || prefixLength > 32) {
                throw new IllegalArgumentException("block size invalid");
            }

            BigInteger mask = buildMask(prefixLength);//mask
            BigInteger ip = wrapBigInteger(inetAddress);//ip
            BigInteger networkIp = ip.and(mask);//network address
            BigInteger broadcastIp = networkIp.add(mask.not());//broadcast address
            this.networkAddress = wrapInetAddress(networkIp);
            this.broadcastAddress = wrapInetAddress(broadcastIp);
            switch (prefixLength) {
                case 31:
                    this.firstAddress = this.networkAddress.getHostAddress();
                    this.lastAddress = this.broadcastAddress.getHostAddress();
                    break;
                case 32:
                    this.firstAddress = this.networkAddress.getHostAddress();
                    this.lastAddress = this.networkAddress.getHostAddress();
                    break;
                default:
                    this.firstAddress = wrapInetAddress(networkIp.add(BigInteger.ONE)).getHostAddress();
                    this.lastAddress = wrapInetAddress(broadcastIp.subtract(BigInteger.ONE)).getHostAddress();
                    break;
            }
        }

        public InetAddress getNetworkAddress() {
            return networkAddress;
        }

        public InetAddress getBroadcastAddress() {
            return broadcastAddress;
        }

        public BigInteger getNetworkAddressValue() {
            return wrapBigInteger(networkAddress);
        }

        public BigInteger getBroadcastAddressValue() {
            return wrapBigInteger(broadcastAddress);
        }

        public String getFirstAddress() {
            return firstAddress;
        }

        public String getLastAddress() {
            return lastAddress;
        }

        public int getMaskLength() {
            return maskLength;
        }
    }

    public static void main(String[] args) {
        System.out.println(CIDRUtil.getSubCIDRBlockByIndex("172.31.64.0/20",24,2));
    }

}
