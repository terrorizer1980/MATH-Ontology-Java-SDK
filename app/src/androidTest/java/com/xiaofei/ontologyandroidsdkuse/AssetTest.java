package com.xiaofei.ontologyandroidsdkuse;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.github.ontio.OntSdk;
import com.github.ontio.sdk.manager.ConnectMgr;
import com.github.ontio.sdk.manager.WalletMgr;
import com.github.ontio.sdk.wallet.Account;
import com.github.ontio.sdk.wallet.Identity;
import com.github.ontio.sdk.wallet.Wallet;
import com.github.ontio.smartcontract.nativevm.Ong;
import com.github.ontio.smartcontract.nativevm.Ont;
import com.github.ontio.smartcontract.nativevm.OntId;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(AndroidJUnit4.class)
public class AssetTest {
    private OntSdk ontSdk;
    private ConnectMgr connectMgr;
    private Ont ont;
    private Ong ong;
    private WalletMgr walletMgr;
    private Wallet wallet;
    private Context appContext;
    private OntId ontIdTx;
    String password = "111111";

    @Before
    public void setUp() throws Exception {
        ontSdk = OntSdk.getInstance();
        ontSdk.setRestful("http://polaris1.ont.io:20334");
        ontSdk.setRestful("http://192.168.50.73:20334");
        appContext  = InstrumentationRegistry.getTargetContext();
        ontSdk.openWalletFile(appContext.getSharedPreferences("wallet",Context.MODE_PRIVATE));
        walletMgr = ontSdk.getWalletMgr();
        wallet = walletMgr.getWallet();
        connectMgr = ontSdk.getConnect();
        ont = ontSdk.nativevm().ont();
        ontIdTx = ontSdk.nativevm().ontId();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void removeAccount() throws Exception {
        List<Account> accounts = wallet.getAccounts();
        int origSize = accounts.size();
        Account account = walletMgr.createAccount("123456");
        assertEquals(accounts.size(),origSize+1);

        wallet.removeAccount(account.address);
        assertEquals(accounts.size(),origSize);

    }

    @Test
    public void removeAccountError(){
        boolean isSuccess =wallet.removeIdentity("");
        assertFalse(isSuccess);
    }

    @Test
    public void removeIdentity() throws Exception {
        List<Identity> identities = wallet.getIdentities();
        int origSize = identities.size();
        Identity identity = walletMgr.createIdentity("123456");
        assertEquals(identities.size(),origSize+1);

        wallet.removeIdentity(identity.ontid);
        assertEquals(identities.size(),origSize);
    }

    @Test
    public void removeIdentityError(){
        boolean isSuccess = wallet.removeIdentity("");
        assertFalse(isSuccess);
    }
}