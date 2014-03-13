package com.nickaknudson.mva.authorities;

import com.nickaknudson.mva.Model;
import com.nickaknudson.mva.clients.FetchClient;

/**
 * @author nick
 *
 * @param <M>
 */
public interface FetchAuthority<M extends Model<M>> extends Authority, FetchClient<M> {

}
