package com.nickaknudson.mva.clients;

import com.nickaknudson.mva.Model;

public interface SRClient<T extends Model<T>> extends SendClient<T>, ReceiveClient<T> {
}
