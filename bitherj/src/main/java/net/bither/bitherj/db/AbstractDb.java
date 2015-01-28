/*
 * Copyright 2014 http://Bither.net
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.bither.bitherj.db;

public abstract class AbstractDb {
    public static final String CREATE_PEER_SQL = "create table if not exists peers " +
            "(peer_address integer primary key" +
            ", peer_port integer not null" +
            ", peer_services integer not null" +
            ", peer_timestamp integer not null" +
            ", peer_connected_cnt integer not null);";

    public static final String CREATE_OUTS_SQL = "create table if not exists outs " +
            "(tx_hash text not null" +
            ", out_sn integer not null" +
            ", out_script text not null" +
            ", out_value integer not null" +
            ", out_status integer not null" +
            ", out_address text" +
            ", primary key (tx_hash, out_sn));";

    public static final String CREATE_INS_SQL = "create table if not exists ins " +
            "(tx_hash text not null" +
            ", in_sn integer not null" +
            ", prev_tx_hash text" +
            ", prev_out_sn integer" +
            ", in_signature text" +
            ", in_sequence integer" +
            ", primary key (tx_hash, in_sn));";
    public static final String CREATE_ADDRESSTXS_SQL = "create table if not exists addresses_txs " +
            "(address text not null" +
            ", tx_hash text not null" +
            ", primary key (address, tx_hash));";
    public static final String CREATE_TXS_SQL = "create table if not exists txs " +
            "(tx_hash text primary key" +
            ", tx_ver integer" +
            ", tx_locktime integer" +
            ", tx_time integer" +
            ", block_no integer" +
            ", source integer);";
    public static final String CREATE_BLOCKS_SQL = "create table if not exists blocks " +
            "(block_no integer not null" +
            ", block_hash text not null primary key" +
            ", block_root text not null" +
            ", block_ver integer not null" +
            ", block_bits integer not null" +
            ", block_nonce integer not null" +
            ", block_time integer not null" +
            ", block_prev text" +
            ", is_main integer not null);";

    public static final String CREATE_PASSWORD_SEED_SQL = "create table if not exists password_seed " +
            "(password_seed text not null primary key);";
    public static final String CREATE_ADDRESSES_SQL = "create table if not exists addresses " +
            "(address text not null primary key" +
            ", encrypt_private_key text" +
            ", pub_key text not null" +
            ", is_xrandom integer not null" +
            ", is_trash integer not null" +
            ", is_synced integer not null" +
            ", sort_time integer not null);";
    public static final String CREATE_HD_SEEDS_SQL = "create table if not exists hd_seeds " +
            "(hd_seed_id integer not null primary key autoincrement" +
            ", encrypt_seed text not null" +
            ", encrypt_hd_seed text" +
            ", hdm_address text not null" +
            ", is_xrandom integer not null);";
    public static final String CREATE_HDM_ADDRESSES_SQL = "create table if not exists hdm_addresses " +
            "(hd_seed_id integer not null" +
            ", hd_seed_index integer not null" +
            ", pub_key_hot text not null" +
            ", pub_key_cold text not null" +
            ", pub_key_remote text" +
            ", address text" +
            ", is_synced integer not null" +
            ", primary key (hd_seed_id, hd_seed_index));";
    public static final String CREATE_HDM_BID_SQL = "create table if not exists hdm_bid " +
            "(hdm_bid text not null primary key" +
            ", encrypt_bither_password text not null);";

    public static final String CREATE_BLOCK_NO_INDEX = "create index idx_blocks_block_no on blocks (block_no);";
    public static final String CREATE_BLOCK_PREV_INDEX = "create index idx_blocks_block_prev on blocks (block_prev);";

    // new index
    public static final String CREATE_OUT_OUT_ADDRESS_INDEX = "create index idx_out_out_address on outs (out_address);";
    public static final String CREATE_TX_BLOCK_NO_INDEX = "create index idx_tx_block_no on txs (block_no);";
    public static final String CREATE_IN_PREV_TX_HASH_INDEX = "create index idx_in_prev_tx_hash on ins (prev_tx_hash);";

    public static IBlockProvider blockProvider;
    public static IPeerProvider peerProvider;
    public static ITxProvider txProvider;
    public static IAddressProvider addressProvider;

    public void construct() {
        blockProvider = initBlockProvider();
        peerProvider = initPeerProvider();
        txProvider = initTxProvider();
        addressProvider = initAddressProvider();
    }

    public abstract IBlockProvider initBlockProvider();

    public abstract IPeerProvider initPeerProvider();

    public abstract ITxProvider initTxProvider();

    public abstract IAddressProvider initAddressProvider();

    public interface Tables {
        public static final String BLOCKS = "blocks";
        public static final String TXS = "txs";
        public static final String ADDRESSES_TXS = "addresses_txs";
        public static final String INS = "ins";
        public static final String OUTS = "outs";
        public static final String PEERS = "peers";
        public static final String Addresses = "addresses";
        public static final String HDSeeds = "hd_seeds";
        public static final String HDMAddresses = "hdm_addresses";
        public static final String HDM_BID = "hdm_bid";
        public static final String PASSWORD_SEED = "password_seed";
    }

    public interface BlocksColumns {
        public static final String BLOCK_NO = "block_no";
        public static final String BLOCK_HASH = "block_hash";
        public static final String BLOCK_ROOT = "block_root";
        public static final String BLOCK_VER = "block_ver";
        public static final String BLOCK_BITS = "block_bits";
        public static final String BLOCK_NONCE = "block_nonce";
        public static final String BLOCK_TIME = "block_time";
        public static final String BLOCK_PREV = "block_prev";
        public static final String IS_MAIN = "is_main";
    }

    public interface TxsColumns {
        public static final String TX_HASH = "tx_hash";
        public static final String TX_VER = "tx_ver";
        public static final String TX_LOCKTIME = "tx_locktime";
        public static final String TX_TIME = "tx_time";
        public static final String BLOCK_NO = "block_no";
        public static final String SOURCE = "source";
    }

    public interface AddressesTxsColumns {
        public static final String ADDRESS = "address";
        public static final String TX_HASH = "tx_hash";
    }

    public interface InsColumns {
        public static final String TX_HASH = "tx_hash";
        public static final String IN_SN = "in_sn";
        public static final String PREV_TX_HASH = "prev_tx_hash";
        public static final String PREV_OUT_SN = "prev_out_sn";
        public static final String IN_SIGNATURE = "in_signature";
        public static final String IN_SEQUENCE = "in_sequence";
    }

    public interface OutsColumns {
        public static final String TX_HASH = "tx_hash";
        public static final String OUT_SN = "out_sn";
        public static final String OUT_SCRIPT = "out_script";
        public static final String OUT_VALUE = "out_value";
        public static final String OUT_STATUS = "out_status";
        public static final String OUT_ADDRESS = "out_address";
    }

    public interface PeersColumns {
        public static final String PEER_ADDRESS = "peer_address";
        public static final String PEER_PORT = "peer_port";
        public static final String PEER_SERVICES = "peer_services";
        public static final String PEER_TIMESTAMP = "peer_timestamp";
        public static final String PEER_CONNECTED_CNT = "peer_connected_cnt";
    }

    public interface PasswordSeedColumns {
        public static final String PASSWORD_SEED = "password_seed";
    }

    public interface AddressesColumns {
        public static final String ADDRESS = "address";
        public static final String ENCRYPT_PRIVATE_KEY = "encrypt_private_key";
        public static final String PUB_KEY = "pub_key";
        public static final String IS_XRANDOM = "is_xrandom";
        public static final String IS_TRASH = "is_trash";
        public static final String IS_SYNCED = "is_synced";
        public static final String SORT_TIME = "sort_time";
    }

    public interface HDSeedsColumns {
        public static final String HD_SEED_ID = "hd_seed_id";
        public static final String ENCRYPT_SEED = "encrypt_seed";
        public static final String ENCRYPT_HD_SEED = "encrypt_HD_seed";
        public static final String IS_XRANDOM = "is_xrandom";
        public static final String HDM_ADDRESS = "hdm_address";
    }

    public interface HDMAddressesColumns {
        public static final String HD_SEED_ID = "hd_seed_id";
        public static final String HD_SEED_INDEX = "hd_seed_index";
        public static final String PUB_KEY_HOT = "pub_key_hot";
        public static final String PUB_KEY_COLD = "pub_key_cold";
        public static final String PUB_KEY_REMOTE = "pub_key_remote";
        public static final String ADDRESS = "address";
        public static final String IS_SYNCED = "is_synced";
    }

    public interface HDMBIdColumns {
        public static final String HDM_BID = "hdm_bid";
        public static final String ENCRYPT_BITHER_PASSWORD = "encrypt_bither_password";
    }
}
