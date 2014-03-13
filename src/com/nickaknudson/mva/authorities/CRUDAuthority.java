package com.nickaknudson.mva.authorities;

import com.nickaknudson.mva.Model;
import com.nickaknudson.mva.clients.CRUDClient;

/**
 * @author nick
 *
 * @param <M>
 */
public interface CRUDAuthority<M extends Model<M>> extends Authority, CRUDClient<M> {

}
