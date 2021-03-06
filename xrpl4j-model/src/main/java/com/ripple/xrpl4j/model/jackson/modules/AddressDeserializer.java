package com.ripple.xrpl4j.model.jackson.modules;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.ripple.xrpl4j.model.transactions.Address;

import java.io.IOException;

public class AddressDeserializer extends StdDeserializer<Address> {

  public AddressDeserializer() {
    super(Address.class);
  }

  @Override
  public Address deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {
    return Address.of(jsonParser.getText());
  }
}
