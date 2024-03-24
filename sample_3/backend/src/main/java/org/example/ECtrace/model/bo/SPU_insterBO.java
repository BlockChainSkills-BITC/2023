package org.example.ECtrace.model.bo;

public class SPU_insterBO {
    String _numid;
    String name;
    String actual_Power;
    String rated_Power;
    String input_Time;
    String position;
    String price;
    String address;

    public String get_numid() {
        return _numid;
    }

    public void set_numid(String _numid) {
        this._numid = _numid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActual_Power() {
        return actual_Power;
    }

    public void setActual_Power(String actual_Power) {
        this.actual_Power = actual_Power;
    }

    public String getRated_Power() {
        return rated_Power;
    }

    public void setRated_Power(String rated_Power) {
        this.rated_Power = rated_Power;
    }

    public String getInput_Time() {
        return input_Time;
    }

    public void setInput_Time(String input_Time) {
        this.input_Time = input_Time;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
