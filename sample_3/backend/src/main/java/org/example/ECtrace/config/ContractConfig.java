package org.example.ECtrace.config;

import java.lang.String;
import lombok.Data;

@Data
public class ContractConfig {
  private String ownableAddress;

  private String tableAddress;

  private String libStringAddress;

  private String energyStorageAddress;

  private String newEnergyAddress;

  private String mapStorageAddress;

  private String solarPanelsStorageAddress;
}
