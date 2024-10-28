package model

import "context"

type State struct {
    id    string
}

func NewState(ctx context.Context, id string) *State {
    return &State{
        id: id,
    }
}

func (s *State) GetId(ctx context.Context) string {
    return s.id
}
