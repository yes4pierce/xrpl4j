package com.ripple.xrpl4j.model.client.accounts;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.primitives.UnsignedInteger;
import com.ripple.xrpl4j.model.client.rippled.XrplResult;
import com.ripple.xrpl4j.model.transactions.Address;
import org.immutables.value.Value;

import java.util.List;
import java.util.Optional;

/**
 * The result of an account_channels rippled call.
 */
@Value.Immutable
@JsonSerialize(as = ImmutableAccountChannelsResult.class)
@JsonDeserialize(as = ImmutableAccountChannelsResult.class)
public interface AccountChannelsResult extends XrplResult {

  /**
   * The {@link Address} of the source/owner of the {@link #channels()}. This corresponds to the
   * {@link AccountChannelsRequestParams#account()} field of the request.
   */
  Address account();

  /**
   * Payment channels owned by this {@link #account()}.
   */
  List<PaymentChannelResultObject> channels();

  /**
   * The identifying Hash of the ledger version used to generate this response.
   */
  @JsonProperty("ledger_hash")
  String ledgerHash();

  /**
   * The Ledger Index of the ledger version used to generate this response.
   */
  @JsonProperty("ledger_index")
  String ledgerIndex();

  /**
   * If true, the information in this response comes from a validated ledger version.
   * Otherwise, the information is subject to change.
   */
  @Value.Default
  default boolean validated() {
    return false;
  }

  /**
   * The limit to how many {@link #channels()} were actually returned by this request.
   */
  Optional<UnsignedInteger> limit();

  /**
   * Server-defined value for pagination. Pass this to the next call to resume getting results where this
   * call left off. Omitted when there are no additional pages after this one.
   */
  Optional<String> marker();

}
