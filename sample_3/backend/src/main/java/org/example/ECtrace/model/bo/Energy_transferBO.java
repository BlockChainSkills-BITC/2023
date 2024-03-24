package org.example.ECtrace.model.bo;

import org.fisco.bcos.sdk.abi.datatypes.generated.Uint256;

public class Energy_transferBO {
    String _from;
    String _to;
    Integer _total;
    String _price;

    public String get_from() {
        return _from;
    }

    public void set_from(String _from) {
        this._from = _from;
    }

    public String get_to() {
        return _to;
    }

    public void set_to(String _to) {
        this._to = _to;
    }

    public Integer get_total() {
        return _total;
    }

    public void set_total(Integer _total) {
        this._total = _total;
    }

    public String get_price() {
        return _price;
    }

    public void set_price(String _price) {
        this._price = _price;
    }
}
