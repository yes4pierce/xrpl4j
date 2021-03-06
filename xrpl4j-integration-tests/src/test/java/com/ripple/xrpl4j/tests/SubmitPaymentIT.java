package com.ripple.xrpl4j.tests;

import static org.assertj.core.api.Assertions.assertThat;

import com.ripple.xrpl4j.client.JsonRpcClientErrorException;
import com.ripple.xrpl4j.client.faucet.FaucetAccountResponse;
import com.ripple.xrpl4j.client.faucet.FundAccountRequest;
import com.ripple.xrpl4j.model.client.accounts.AccountInfoResult;
import com.ripple.xrpl4j.model.client.fees.FeeResult;
import com.ripple.xrpl4j.model.client.transactions.SubmitResult;
import com.ripple.xrpl4j.model.transactions.Payment;
import com.ripple.xrpl4j.model.transactions.XrpCurrencyAmount;
import com.ripple.xrpl4j.wallet.Wallet;
import org.junit.jupiter.api.Test;

public class SubmitPaymentIT extends AbstractIT {

  @Test
  public void sendPayment() throws JsonRpcClientErrorException {
    Wallet sourceWallet = createRandomAccount();
    Wallet destinationWallet = createRandomAccount();

    FaucetAccountResponse fundDestinationResponse =
        faucetClient.fundAccount(FundAccountRequest.of(destinationWallet.classicAddress().value()));

    assertThat(fundDestinationResponse.amount()).isGreaterThan(0);

    FeeResult feeResult = xrplClient.fee();
    AccountInfoResult accountInfo = this.scanForResult(() -> this.getValidatedAccountInfo(sourceWallet.classicAddress()));
    Payment payment = Payment.builder()
        .account(sourceWallet.classicAddress())
        .fee(feeResult.drops().openLedgerFee())
        .sequence(accountInfo.accountData().sequence())
        .destination(destinationWallet.classicAddress())
        .amount(XrpCurrencyAmount.ofDrops(12345))
        .signingPublicKey(sourceWallet.publicKey())
        .build();

    SubmitResult<Payment> result = xrplClient.submit(sourceWallet, payment);
    assertThat(result.engineResult()).isNotEmpty().get().isEqualTo("tesSUCCESS");
    logger.info("Payment successful: https://testnet.xrpl.org/transactions/" + result.transaction().hash().orElse("n/a"));

    this.scanForResult(
        () -> this.getValidatedTransaction(
            result.transaction().hash()
                .orElseThrow(() -> new RuntimeException("Could not look up Payment because the result did not have a hash.")),
            Payment.class)
    );
  }

  @Test
  public void sendPaymentFromSecp256k1Wallet() throws JsonRpcClientErrorException {
    Wallet senderWallet = walletFactory.fromSeed("sp5fghtJtpUorTwvof1NpDXAzNwf5", true);
    logger.info("Generated source testnet wallet with address " + senderWallet.xAddress());

    FaucetAccountResponse fundResponse =
        faucetClient.fundAccount(FundAccountRequest.of(senderWallet.classicAddress().value()));
    logger.info("Source account has been funded");
    assertThat(fundResponse.amount()).isGreaterThan(0);

    Wallet destinationWallet = createRandomAccount();

    FeeResult feeResult = xrplClient.fee();
    AccountInfoResult accountInfo = this.scanForResult(() -> this.getValidatedAccountInfo(senderWallet.classicAddress()));

    Payment payment = Payment.builder()
        .account(senderWallet.classicAddress())
        .fee(feeResult.drops().openLedgerFee())
        .sequence(accountInfo.accountData().sequence())
        .destination(destinationWallet.classicAddress())
        .amount(XrpCurrencyAmount.ofDrops(12345))
        .signingPublicKey(senderWallet.publicKey())
        .build();

    SubmitResult<Payment> result = xrplClient.submit(senderWallet, payment);
    assertThat(result.engineResult()).isNotEmpty().get().isEqualTo("tesSUCCESS");
    logger.info("Payment successful: https://testnet.xrpl.org/transactions/" + result.transaction().hash().orElse("n/a"));

    this.scanForResult(
        () -> this.getValidatedTransaction(
            result.transaction().hash()
                .orElseThrow(() -> new RuntimeException("Could not look up Payment because the result did not have a hash.")),
            Payment.class)
    );
  }

}
