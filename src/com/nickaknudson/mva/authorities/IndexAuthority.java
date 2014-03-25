package com.nickaknudson.mva.authorities;

import com.nickaknudson.mva.Model;
import com.nickaknudson.mva.clients.IndexClient;

/**
 * @author nick
 * 
 * @param <M>
 */
public interface IndexAuthority<M extends Model<M>> extends IndexClient<M> {

}
