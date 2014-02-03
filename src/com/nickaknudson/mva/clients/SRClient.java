package com.nickaknudson.mva.clients;

import com.nickaknudson.mva.Model;

/**
 * @author nick
 *
 * @param <M>
 */
public interface SRClient<M extends Model<M>> extends SendClient<M>, ReceiveClient<M> {
}
