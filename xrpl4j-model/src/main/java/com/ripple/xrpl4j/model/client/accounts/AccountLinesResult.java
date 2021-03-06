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
 * The result of an account_lines rippled method call.
 */
@Value.Immutable
@JsonSerialize(as = ImmutableAccountLinesResult.class)
@JsonDeserialize(as = ImmutableAccountLinesResult.class)
public interface AccountLinesResult extends XrplResult {

  /**
   * The unique {@link Address} for the account that made the request.
   */
  Address account();

  /**
   * A {@link List} of {@link TrustLine}s.  If the number of {@link TrustLine}s for this account is large,
   * this will contain up to {@link AccountLinesRequestParams#limit()} entries.
   */
  List<TrustLine> lines();

  /**
   * The ledger index of the current open ledger, which was used when retrieving this information.
   */
  @JsonProperty("ledger_current_index")
  Optional<UnsignedInteger> ledgerCurrentIndex();

  /**
   * The ledger index of the ledger version that was used when retrieving this data.
   */
  @JsonProperty("ledger_index")
  Optional<UnsignedInteger> ledgerIndex();

  /**
   * The identifying hash the ledger version that was used when retrieving this data.
   */
  @JsonProperty("ledger_hash")
  Optional<String> ledgerHash();

  /**
   * Server-defined value indicating the response is paginated. Pass this to the next call to resume where this
   * call left off. Omitted when there are no additional pages after this one.
   */
  Optional<String> marker();

}
