package com.ripple.xrpl4j.model.jackson.modules;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import com.ripple.xrpl4j.model.transactions.XrpCurrencyAmount;

import java.io.IOException;

public class XrpCurrencyAmountSerializer extends StdScalarSerializer<XrpCurrencyAmount> {

  public XrpCurrencyAmountSerializer() {
    super(XrpCurrencyAmount.class, false);
  }

  @Override
  public void serialize(
      XrpCurrencyAmount xrpCurrencyAmount,
      JsonGenerator gen,
      SerializerProvider provider
  ) throws IOException {
    gen.writeString(xrpCurrencyAmount.toString());
  }
}
