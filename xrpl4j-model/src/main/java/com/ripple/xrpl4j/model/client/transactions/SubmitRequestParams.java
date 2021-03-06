package com.ripple.xrpl4j.model.client.transactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ripple.xrpl4j.model.client.rippled.XrplRequestParams;
import org.immutables.value.Value;

/**
 * Request parameters for the submit API method.
 */
@Value.Immutable
@JsonSerialize(as = ImmutableSubmitRequestParams.class)
@JsonDeserialize(as = ImmutableSubmitRequestParams.class)
public interface SubmitRequestParams extends XrplRequestParams {

  static SubmitRequestParams of(String blobHex) {
    return ImmutableSubmitRequestParams.builder().txBlob(blobHex).build();
  }

  /**
   * The hex encoded {@link String} containing a signed, binary encoded transaction.
   */
  @JsonProperty("tx_blob")
  String txBlob();

}
